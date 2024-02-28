package bg.duosoft.bpo.patent_classification.be.controller.v1;


import bg.duosoft.bpo.common.security.util.SecurityUtils;
import bg.duosoft.bpo.patent_classification.be.domain.dto.LogDTO;
import bg.duosoft.bpo.patent_classification.be.service.cpc.CPCLatestService;
import bg.duosoft.bpo.patent_classification.be.service.cpc.CPCService;
import bg.duosoft.bpo.patent_classification.be.service.cpc.ConcordanceService;
import bg.duosoft.bpo.patent_classification.be.service.log.PatentClassificationLogService;
import bg.duosoft.bpo.patent_classification.be.util.Activity;
import bg.duosoft.bpo.patent_classification.be.util.ActivityToken;
import bg.duosoft.bpo.patent_classification.be.util.cpc.CpcFilesEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/v1/cpc")
@RequiredArgsConstructor
public class CpcLoaderController {

    private final ActivityToken activityToken;
    private final CPCLatestService cpcLatestService;
    private final CPCService cpcService;
    private final ConcordanceService concordanceService;
    private final PatentClassificationLogService logService;

    @Value("${cpc.files.location}")
    private String cpcFilesLocation;

    @PostMapping("/upload")
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_CPC)")
    public ResponseEntity<?> handleFileUploadCpc(@RequestParam("file") MultipartFile file, @RequestParam("fileType") String fileType) {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Upload CPC file");
            logDTO.setNewData(fileType + " - " + file.getOriginalFilename());
            logDTO.setResponsibleUser(SecurityUtils.getUsername());

            CpcFilesEnum cpcFilesEnum = CpcFilesEnum.valueOf(fileType);
            activityToken.setCurrentProcess("handleFileUploadCpc" + fileType);
            try {
                Activity activity = new Activity("l.load.cpc.file." + fileType, LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getCpcProcessHistory().add(activity);
                file.transferTo(new File(cpcFilesLocation + cpcFilesEnum.getValue()));
                finishSuccessfulProcess(logDTO);
            } catch (Exception e) {
                activityToken.getCpcProcessHistory().get(activityToken.getCpcProcessHistory().size() - 1).setEndTime(LocalDateTime.now());
                activityToken.getCpcProcessHistory().get(activityToken.getCpcProcessHistory().size() - 1).setStatus("l.fail");
                setCpcFileName(cpcFilesEnum, null);
                activityToken.setCurrentProcess(null);
                e.printStackTrace();
                logDTO.setOperationStatus("FAIL");
                logDTO.setNote(e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            } finally {
                logService.createLog(logDTO);
                activityToken.setCurrentProcess(null);
            }
            setCpcFileName(cpcFilesEnum, file.getOriginalFilename());
            return ResponseEntity.ok("l.load.cpc.file." + fileType);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(path = "/download/{fileType}")
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_CPC)")
    public ResponseEntity<Resource> download(@PathVariable String fileType) throws IOException {
        File cpcFile = new File(cpcFilesLocation + CpcFilesEnum.valueOf(fileType).getValue());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(cpcFile));

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + CpcFilesEnum.valueOf(fileType).getValue());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(cpcFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @DeleteMapping("/delete-file/{fileType}")
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_CPC)")
    public void deleteCpcFile(@PathVariable("fileType") String fileType) throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Delete CPC file");
            logDTO.setOldData(fileType);
            logDTO.setResponsibleUser(SecurityUtils.getUsername());

            activityToken.setCurrentProcess("deleteCpcFile" + fileType);
            try {
                Activity activity = new Activity("l.delete.cpc.file." + fileType, LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getCpcProcessHistory().add(activity);
                File file = new File(cpcFilesLocation + CpcFilesEnum.valueOf(fileType).getValue());
                if (file.delete()) {
                    setCpcFileName(CpcFilesEnum.valueOf(fileType), null);
                    finishSuccessfulProcess(logDTO);
                } else {
                    throw new Exception("File not found!");
                }
            } catch (Exception e) {
                processException(e, logDTO);
            } finally {
                logService.createLog(logDTO);
                activityToken.setCurrentProcess(null);
            }
        }
    }

    @PostMapping(value = "/save-new-cpc-entries")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_CPC)")
    public void saveNewCpcEntries(@RequestParam("latestVersion") String latestVersion) throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Save new CPC entries");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("CF_CLASS_CPC_LATEST_SPEC");

            activityToken.setCurrentProcess("saveNewCpcEntries");
            activityToken.setCpcLatestVersion(latestVersion);
            try {
                Activity activity = new Activity("l.save.new.cpc.entries", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getCpcProcessHistory().add(activity);
                cpcLatestService.saveNewCpcEntries(latestVersion);
//                Thread.sleep(5000);
                finishSuccessfulProcess(logDTO);
            } catch (Exception e) {
                processException(e, logDTO);
            } finally {
                activityToken.setCurrentProcess(null);
                logService.createLog(logDTO);
            }
        }
    }

    @PostMapping(value = "/delete-unused-cpc-classes")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_CPC)")
    public void deleteUnusedCpcClasses() throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Delete unused CPC entries");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("CF_CLASS_CPC");

            activityToken.setCurrentProcess("deleteUnusedCpcClasses");
            try {
                Activity activity = new Activity("l.delete.unused.cpc", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getCpcProcessHistory().add(activity);
                cpcService.deleteUnusedCpcClasses();
//                Thread.sleep(5000);
                finishSuccessfulProcess(logDTO);
            } catch (Exception e) {
                processException(e, logDTO);
            } finally {
                logService.createLog(logDTO);
                activityToken.setCurrentProcess(null);
            }
        }
    }

    @PostMapping(value = "/update-valid-cpc-classes")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_CPC)")
    public void updateValidCpcClasses() throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Update valid CPC entries");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("CF_CLASS_CPC");

            activityToken.setCurrentProcess("updateValidCpcClasses");
            try {
                Activity activity = new Activity("l.update.valid.cpc", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getCpcProcessHistory().add(activity);
                cpcService.updateValidCpcClasses();
//                Thread.sleep(5000);
                finishSuccessfulProcess(logDTO);
            } catch (Exception e) {
                processException(e, logDTO);
            } finally {
                logService.createLog(logDTO);
                activityToken.setCurrentProcess(null);
            }
        }
    }

    @PostMapping(value = "/add-missing-cpc-classes")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_CPC)")
    public void addMissingCpcClasses() throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Add missing CPC entries");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("CF_CLASS_CPC");

            activityToken.setCurrentProcess("addMissingCpcClasses");
            try {
                Activity activity = new Activity("l.add.missing.cpc", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getCpcProcessHistory().add(activity);
                cpcService.addMissingCpcClasses();
//                Thread.sleep(5000);
                finishSuccessfulProcess(logDTO);
            } catch (Exception e) {
                processException(e, logDTO);
            } finally {
                logService.createLog(logDTO);
                activityToken.setCurrentProcess(null);
            }
        }
    }

    @PostMapping(value = "/fill-concordance-table")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_CPC)")
    public void fillConcordanceTable(@RequestParam("latestVersion") String latestVersion) throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Fill concordance table");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("CF_CLASS_CPC_IPC_CONCORDANCE");

            activityToken.setCurrentProcess("fillConcordanceTable");
            activityToken.setCpcLatestVersion(latestVersion);
            try {
                Activity activity = new Activity("l.fill.concordance", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getCpcProcessHistory().add(activity);
                concordanceService.fillConcordanceTable(latestVersion);
//                Thread.sleep(5000);
                finishSuccessfulProcess(logDTO);
            } catch (Exception e) {
                processException(e, logDTO);
            } finally {
                logService.createLog(logDTO);
                activityToken.setCurrentProcess(null);
            }
        }
    }

    @PostMapping(value = "/finalize-cpc-update")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_CPC)")
    public void finalizeCpcUpdate(@RequestParam("latestVersion") String latestVersion) throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Finalize CPC update");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("EXT_CONFIG_PARAM");

            activityToken.setCurrentProcess("finalizeCpcUpdate");
            activityToken.setCpcLatestVersion(latestVersion);
            try {
                Activity activity = new Activity("l.finalize.cpc.update", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getCpcProcessHistory().add(activity);
                cpcService.finalizeCpcUpdate(latestVersion);
//                Thread.sleep(5000);
                finishSuccessfulProcess(logDTO);
            } catch (Exception e) {
                processException(e, logDTO);
            } finally {
                logService.createLog(logDTO);
                activityToken.setCurrentProcess(null);
            }
        }
    }

    private void finishSuccessfulProcess(LogDTO logDTO) {
        activityToken.getCpcProcessHistory().get(activityToken.getCpcProcessHistory().size() - 1).setEndTime(LocalDateTime.now());
        activityToken.getCpcProcessHistory().get(activityToken.getCpcProcessHistory().size() - 1).setStatus("l.success");
        logDTO.setOperationStatus("OK");
    }

    private void processException(Exception e, LogDTO logDTO) throws Exception {
        activityToken.getCpcProcessHistory().get(activityToken.getCpcProcessHistory().size() - 1).setEndTime(LocalDateTime.now());
        activityToken.getCpcProcessHistory().get(activityToken.getCpcProcessHistory().size() - 1).setStatus("l.fail");
        activityToken.setCurrentProcess(null);
        logDTO.setOperationStatus("FAIL");
        logDTO.setNote(e.getMessage());
        e.printStackTrace();
        throw e;
    }

    private void setCpcFileName(CpcFilesEnum type, String fileName) {
        switch (type) {
            case TITLE_A -> activityToken.setTitleAFileName(fileName);
            case TITLE_B -> activityToken.setTitleBFileName(fileName);
            case TITLE_C -> activityToken.setTitleCFileName(fileName);
            case TITLE_D -> activityToken.setTitleDFileName(fileName);
            case TITLE_E -> activityToken.setTitleEFileName(fileName);
            case TITLE_F -> activityToken.setTitleFFileName(fileName);
            case TITLE_G -> activityToken.setTitleGFileName(fileName);
            case TITLE_H -> activityToken.setTitleHFileName(fileName);
            case TITLE_Y -> activityToken.setTitleYFileName(fileName);
            case VALIDITY -> activityToken.setValidityFileName(fileName);
            case CONCORDANCE -> activityToken.setConcordanceFileName(fileName);
            default -> throw new RuntimeException("Unrecognized CpcFilesEnum type");
        }

    }

}
