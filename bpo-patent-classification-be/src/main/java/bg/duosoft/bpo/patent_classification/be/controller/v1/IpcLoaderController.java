package bg.duosoft.bpo.patent_classification.be.controller.v1;

import bg.duosoft.bpo.common.security.util.SecurityUtils;
import bg.duosoft.bpo.patent_classification.be.domain.dto.LogDTO;
import bg.duosoft.bpo.patent_classification.be.service.ipc.IPCLatestService;
import bg.duosoft.bpo.patent_classification.be.service.ipc.IPCService;
import bg.duosoft.bpo.patent_classification.be.service.log.PatentClassificationLogService;
import bg.duosoft.bpo.patent_classification.be.util.Activity;
import bg.duosoft.bpo.patent_classification.be.util.ActivityToken;
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
@RequestMapping("/api/v1/ipc")
@RequiredArgsConstructor
public class IpcLoaderController {

    private final ActivityToken activityToken;
    private final IPCLatestService ipcLatestService;
    private final IPCService ipcService;
    private final PatentClassificationLogService logService;

    @Value("${xml.schema.file.location}")
    private String xmlFileLocation;

    @PostMapping("/upload")
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_IPC)")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Upload IPC schema");
            logDTO.setNewData(file.getOriginalFilename());
            logDTO.setResponsibleUser(SecurityUtils.getUsername());

            activityToken.setCurrentProcess("handleFileUpload");
            try {
                Activity activity = new Activity("l.load.file", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getIpcProcessHistory().add(activity);
                file.transferTo(new File(xmlFileLocation));
                finishSuccessfulProcess(logDTO);
            } catch (Exception e) {
                activityToken.getIpcProcessHistory().get(activityToken.getIpcProcessHistory().size() - 1).setEndTime(LocalDateTime.now());
                activityToken.getIpcProcessHistory().get(activityToken.getIpcProcessHistory().size() - 1).setStatus("l.fail");
                activityToken.setIpcXmlSchemaFileName(null);
                activityToken.setCurrentProcess(null);
                e.printStackTrace();
                logDTO.setOperationStatus("FAIL");
                logDTO.setNote(e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            } finally {
                logService.createLog(logDTO);
                activityToken.setCurrentProcess(null);
            }
            activityToken.setIpcXmlSchemaFileName(file.getOriginalFilename());
            return ResponseEntity.ok("load.file.success");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(path = "/download")
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_IPC)")
    public ResponseEntity<Resource> download() throws IOException {
        File xmlFile = new File(xmlFileLocation);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(xmlFile));

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ipc_schema.xml");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(xmlFile.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @DeleteMapping("/delete-schema-file")
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_IPC)")
    public void deleteXmlSchema() throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Delete IPC schema");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());

            activityToken.setCurrentProcess("deleteXmlSchema");
            try {
                Activity activity = new Activity("l.delete.file", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getIpcProcessHistory().add(activity);
                File file = new File(xmlFileLocation);
                if (file.delete()) {
                    activityToken.setIpcXmlSchemaFileName(null);
                }
                finishSuccessfulProcess(logDTO);
            } catch (Exception e) {
                processException(e, logDTO);
            } finally {
                logService.createLog(logDTO);
                activityToken.setCurrentProcess(null);
            }
        }
    }

    @PostMapping(value = "/save-all-xml-entries")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_IPC)")
    public void saveAllXmlEntries() throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Save new IPC entries");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("CF_CLASS_IPC_LATEST_SPEC");

            activityToken.setCurrentProcess("saveAllXmlEntries");
            try {
                Activity activity = new Activity("l.save.xml.entries", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getIpcProcessHistory().add(activity);
                ipcLatestService.saveAllXmlEntries();
//                Thread.sleep(5000);
                finishSuccessfulProcess(logDTO);
            } catch (/*JAXB*/Exception e) {
                processException(e, logDTO);
            } finally {
                logService.createLog(logDTO);
                activityToken.setCurrentProcess(null);
            }
        }
    }

    @PostMapping(value = "/delete-unused-ipc-classes")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_IPC)")
    public void deleteUnusedIpcClasses() throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Delete unused IPC entries");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("CF_CLASS_IPC");

            activityToken.setCurrentProcess("deleteUnusedIpcClasses");
            try {
                Activity activity = new Activity("l.delete.unused.ipc", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getIpcProcessHistory().add(activity);
                ipcService.deleteUnusedIpcClasses();
//                Thread.sleep(15000);
                finishSuccessfulProcess(logDTO);
            } catch (Exception e) {
                processException(e, logDTO);
            } finally {
                logService.createLog(logDTO);
                activityToken.setCurrentProcess(null);
            }
        }
    }

    @PostMapping(value = "/normalize-ipc-classes")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_IPC)")
    public void normalizeIpcClasses() throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Normalize IPC entries");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("CF_CLASS_IPC, IP_PATENT_IPC_CLASSES");

            activityToken.setCurrentProcess("normalizeIpcClasses");
            try {
                Activity activity = new Activity("l.normalize.ipc", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getIpcProcessHistory().add(activity);
                ipcService.normalizeRepeatedIpcClasses();
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

    @PostMapping(value = "/take-latest-for-duplicate-ipcs")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_IPC)")
    public void takeLatestForDuplicateIpcs() throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Take latest for duplicate IPC entries");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("CF_CLASS_IPC, IP_PATENT_IPC_CLASSES");

            activityToken.setCurrentProcess("takeLatestForDuplicateIpcs");
            try {
                Activity activity = new Activity("l.latest.version.duplicates", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getIpcProcessHistory().add(activity);
                ipcService.takeLatestForDuplicateIpcs();
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

    @PostMapping(value = "/update-edition-of-valid-ipc-classes")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_IPC)")
    public void updateEditionOfValidIpcClasses() throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Update edition of IPC entries");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("CF_CLASS_IPC");

            activityToken.setCurrentProcess("updateEditionOfValidIpcClasses");
            try {
                Activity activity = new Activity("l.update.valid.ipcs", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getIpcProcessHistory().add(activity);
                ipcService.updateEditionOfValidIpcClasses();
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

    @PostMapping(value = "/fix-invalid-names")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_IPC)")
    public void fixInvalidNames() throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Fix invalid names of IPC entries");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("CF_CLASS_IPC");

            activityToken.setCurrentProcess("fixInvalidNames");
            try {
                Activity activity = new Activity("l.update.invalid.names", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getIpcProcessHistory().add(activity);
                ipcService.fixInvalidNames();
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

    @PostMapping(value = "/add-missing-ipc-classes")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_IPC)")
    public void addMissingIpcClasses() throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Add missing IPC entries");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("CF_CLASS_IPC");

            activityToken.setCurrentProcess("addMissingIpcClasses");
            try {
                Activity activity = new Activity("l.add.missing.ipcs", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getIpcProcessHistory().add(activity);
                ipcService.addMissingIpcClasses();
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

    @PostMapping(value = "/finalize-ipc-update")
    @ResponseBody
    @PreAuthorize("hasRole(T(bg.duosoft.bpo.common.security.util.SecurityRole).ADMIN_PATENT_CLASSIFICATION_IPC)")
    public void finalizeIpcUpdate() throws Exception {
        if (!StringUtils.hasText(activityToken.getCurrentProcess())) {

            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Finalize IPC update");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("EXT_CONFIG_PARAM");

            activityToken.setCurrentProcess("finalizeIpcUpdate");
            try {
                Activity activity = new Activity("l.finalize.ipc", LocalDateTime.now(), null, SecurityUtils.getUsername(), "l.in.progress");
                activityToken.getIpcProcessHistory().add(activity);
                ipcService.finalizeIpcUpdate();
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
        activityToken.getIpcProcessHistory().get(activityToken.getIpcProcessHistory().size() - 1).setEndTime(LocalDateTime.now());
        activityToken.getIpcProcessHistory().get(activityToken.getIpcProcessHistory().size() - 1).setStatus("l.success");
        logDTO.setOperationStatus("OK");
    }

    private void processException(Exception e, LogDTO logDTO) throws Exception {
        activityToken.getIpcProcessHistory().get(activityToken.getIpcProcessHistory().size() - 1).setEndTime(LocalDateTime.now());
        activityToken.getIpcProcessHistory().get(activityToken.getIpcProcessHistory().size() - 1).setStatus("l.fail");
        activityToken.setCurrentProcess(null);
        logDTO.setOperationStatus("FAIL");
        logDTO.setNote(e.getMessage());
        e.printStackTrace();
        throw e;
    }
}
