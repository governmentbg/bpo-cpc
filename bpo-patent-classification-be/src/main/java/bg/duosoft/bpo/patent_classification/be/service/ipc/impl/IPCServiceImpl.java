package bg.duosoft.bpo.patent_classification.be.service.ipc.impl;

import bg.duosoft.bpo.common.dto.filter.AutocompleteFilterDTO;
import bg.duosoft.bpo.common.security.util.SecurityUtils;
import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcTableRowDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.LogDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.filter.IpcClassesFilterDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCClassEntity;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCEntityPK;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCLatestEntity;
import bg.duosoft.bpo.patent_classification.be.mapper.IpcMapper;
import bg.duosoft.bpo.patent_classification.be.mapper.IpcTableRowMapper;
import bg.duosoft.bpo.patent_classification.be.repository.ipc.IPCRepository;
import bg.duosoft.bpo.patent_classification.be.repository.ipc.IPCRepositoryCustom;
import bg.duosoft.bpo.patent_classification.be.service.ipc.IPCLatestService;
import bg.duosoft.bpo.patent_classification.be.service.ipc.IPCService;
import bg.duosoft.bpo.patent_classification.be.service.log.PatentClassificationLogService;
import bg.duosoft.bpo.patent_classification.be.service.patent_ipc.PatentIpcService;
import bg.duosoft.bpo.patent_classification.be.util.Utils;
import bg.duosoft.bpo.patent_classification.be.util.ipc.IPCCodeWithoutEdition;
import bg.duosoft.bpo.patent_classification.be.util.ipc.IPCWithDifferentEdition;
import bg.duosoft.bpo.patent_classification.be.util.ipc.NameUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@RequiredArgsConstructor
public class IPCServiceImpl implements IPCService {
    private final IPCRepository ipcRepository;
    private final IPCRepositoryCustom ipcRepositoryCustom;
    private final PatentIpcService patentIpcService;
    private final IPCLatestService ipcLatestService;
    private final IpcTableRowMapper ipcTableRowMapper;
    private final IpcMapper ipcMapper;
    private final PatentClassificationLogService logService;

    @Override
    public List<IPCClassEntity> selectAllWithSameCode(String ipcSectionCode, String ipcClassCode, String ipcSubclassCode, String ipcGroupCode, String ipcSubgroupCode) {
        return ipcRepository.selectAllWithSameCode(ipcSectionCode, ipcClassCode, ipcSubclassCode, ipcGroupCode, ipcSubgroupCode);
    }

    @Override
    public void deleteUnusedIpcClasses() {
        log.info("################# deleteUnusedIpcClasses Started! #################");
        ipcRepositoryCustom.deleteUnusedIpcClasses();
        log.info("################# deleteUnusedIpcClasses Completed! #################");
    }

    @Override
    public void normalizeRepeatedIpcClasses() {
        log.info("################# normalizeRepeatedIpcClasses Started! #################");
        List<IPCCodeWithoutEdition> repeatingEntities = ipcRepository.getRepeatingEntitiesWithDifferentNames();
        for (IPCCodeWithoutEdition x : repeatingEntities) {
            try {
                List<IPCClassEntity> sameIpcEntities = selectAllWithSameCode(x.getIpcSectionCode(), x.getIpcClassCode(), x.getIpcSubclassCode(), x.getIpcGroupCode(), x.getIpcSubgroupCode());

                IPCClassEntity maxValidIpc = sameIpcEntities.stream().filter(a -> !a.getIpcName().contains("??") && !a.getIpcName().equals("Unknown")).max(new IPCComparator())
                        .orElse(null);

                if (Objects.isNull(maxValidIpc)) {
                    maxValidIpc = sameIpcEntities.stream().max(new IPCComparator()).orElse(null);
                }

                if (Objects.nonNull(maxValidIpc)) {
                    List<IPCClassEntity> questionMarkIpcs = sameIpcEntities.stream().filter(a -> a.getIpcName().contains("??") || a.getIpcName().equals("??") || a.getIpcName().equals("Unknown")).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(questionMarkIpcs)) {
                        for (IPCClassEntity qm : questionMarkIpcs) {
                            qm.setIpcName(maxValidIpc.getIpcName());
                        }
                        ipcRepository.saveAll(questionMarkIpcs);
                    }
                } else {
                    log.info("Could not find max valid IPC for " + x.getIpcSectionCode() + x.getIpcClassCode() + x.getIpcSubclassCode() + x.getIpcGroupCode() + x.getIpcSubgroupCode());
                }
            } catch (Exception e) {
                throw new RuntimeException("Error occurred during normalizeRepeatedIpcClasses()!: " + Arrays.toString(e.getStackTrace()));
            }
        }

        int i = 0;
        List<IPCCodeWithoutEdition> repeatingEntitiesWithSameName = ipcRepository.getRepeatingEntities();
        for (IPCCodeWithoutEdition x : repeatingEntitiesWithSameName) {
            log.info("normalizing " + (i++) + " / " + repeatingEntitiesWithSameName.size() + " -> " + x.getIpcSectionCode() + x.getIpcClassCode() + x.getIpcSubclassCode() + x.getIpcGroupCode() + x.getIpcSubgroupCode());
            try {
                List<IPCClassEntity> sameIpcEntities = selectAllWithSameCode(x.getIpcSectionCode(), x.getIpcClassCode(), x.getIpcSubclassCode(), x.getIpcGroupCode(), x.getIpcSubgroupCode());

                Map<String, List<IPCClassEntity>> ipcsPerName = sameIpcEntities.stream()
                        .collect(groupingBy(ipcClassEntity -> NameUtil.clearIpcName(ipcClassEntity.getIpcName())));

                for (Map.Entry<String, List<IPCClassEntity>> entry : ipcsPerName.entrySet()) {
                    List<IPCClassEntity> entries = entry.getValue();

                    IPCClassEntity maxByEdition = entries
                            .stream()
                            .max(new IPCComparator())
                            .orElse(null);

                    if (Objects.nonNull(maxByEdition)) {
                        entries.remove(maxByEdition);
                        for (IPCClassEntity entityToDelete : entries) {
                            patentIpcService.updateEntries(entityToDelete.getIpcEntityPK().getIpcSectionCode(), entityToDelete.getIpcEntityPK().getIpcClassCode(), entityToDelete.getIpcEntityPK().getIpcSubclassCode(), entityToDelete.getIpcEntityPK().getIpcGroupCode(), entityToDelete.getIpcEntityPK().getIpcSubgroupCode(), entityToDelete.getIpcEntityPK().getIpcEditionCode(), maxByEdition.getIpcEntityPK().getIpcEditionCode());
                            ipcRepository.delete(entityToDelete);
                        }
                    } else {
                        log.info("Could not find max IPC for edition for " + x.getIpcSectionCode() + x.getIpcClassCode() + x.getIpcSubclassCode() + x.getIpcGroupCode() + x.getIpcSubgroupCode());
                    }

                }
            } catch (Exception e) {
                throw new RuntimeException("Error occurred during normalizeRepeatedIpcClasses()!: " + Arrays.toString(e.getStackTrace()));
            }
        }
        log.info("################# normalizeRepeatedIpcClasses Completed! #################");
    }

    @Override
    public void takeLatestForDuplicateIpcs() {
        log.info("################# takeLatestForDuplicateIpcs Started! #################");
        List<IPCCodeWithoutEdition> repeatingEntities = ipcRepository.getRepeatingEntities();
        for (IPCCodeWithoutEdition x : repeatingEntities) {
            try {
                List<IPCClassEntity> sameIpcEntities = selectAllWithSameCode(x.getIpcSectionCode(), x.getIpcClassCode(), x.getIpcSubclassCode(), x.getIpcGroupCode(), x.getIpcSubgroupCode());

                IPCClassEntity maxValidIpc = sameIpcEntities.stream().filter(a -> !a.getIpcName().contains("??") && !a.getIpcName().equals("Unknown")).max(new IPCComparator())
                        .orElse(null);

                if (Objects.isNull(maxValidIpc)) {
                    maxValidIpc = sameIpcEntities.stream().max(new IPCComparator()).orElse(null);
                }

                if (Objects.nonNull(maxValidIpc)) {
                    sameIpcEntities.remove(maxValidIpc);
                    for (IPCClassEntity entityToDelete : sameIpcEntities) {
                        patentIpcService.updateEntries(entityToDelete.getIpcEntityPK().getIpcSectionCode(), entityToDelete.getIpcEntityPK().getIpcClassCode(), entityToDelete.getIpcEntityPK().getIpcSubclassCode(), entityToDelete.getIpcEntityPK().getIpcGroupCode(), entityToDelete.getIpcEntityPK().getIpcSubgroupCode(), entityToDelete.getIpcEntityPK().getIpcEditionCode(), maxValidIpc.getIpcEntityPK().getIpcEditionCode());
                        ipcRepository.delete(entityToDelete);
                    }
                } else {
                    log.info("Could not find latest IPC for edition for " + x.getIpcSectionCode() + x.getIpcClassCode() + x.getIpcSubclassCode() + x.getIpcGroupCode() + x.getIpcSubgroupCode());
                }

            } catch (Exception e) {
                throw new RuntimeException("Error occurred during takeLatestForDuplicateIpcs()!: " + e.getMessage());
            }
        }
        log.info("################# takeLatestForDuplicateIpcs Completed! #################");
    }

    @Override
    public void updateEditionOfValidIpcClasses() {
        log.info("################# update Ipc lasses from 2006 Started! #################");
        List<IPCWithDifferentEdition> entitiesFrom2006 = ipcRepository.getEntitiesWithDifferentNamesFrom2006();
        int i = 0;
        int size = entitiesFrom2006.size();
        for (IPCWithDifferentEdition ipc : entitiesFrom2006) {
            log.info("updating entity from 2006 - " + (i++) + " / " + size + " -> " + ipc.getIpcSectionCode() + ipc.getIpcClassCode() + ipc.getIpcSubclassCode() + ipc.getIpcGroupCode() + ipc.getIpcSubgroupCode());
            ipcRepository.updateEditionAndLatestVersion(ipc.getNewIpcEditionCode(), ipc.getNewIpcLatestVersion(), ipc.getNewIpcName(), ipc.getIpcEditionCode(), ipc.getIpcSectionCode(), ipc.getIpcClassCode(), ipc.getIpcSubclassCode(), ipc.getIpcGroupCode(), ipc.getIpcSubgroupCode());
        }
        log.info("################# update Ipc lasses from 2006 Completed! #################");
        log.info("################# updateEditionOfValidIpcClasses Started! #################");
        List<IPCWithDifferentEdition> entitiesWithDifferentEditionCodes = ipcRepository.getEntitiesWithDifferentEditionCodes();
        i = 0;
        size = entitiesWithDifferentEditionCodes.size();
        for (IPCWithDifferentEdition ipc : entitiesWithDifferentEditionCodes) {
            log.info((i++) + " / " + size);
            if (NameUtil.ipcNameEquals2(ipc.getIpcName(), ipc.getNewIpcName())) {
                log.info("updating edition -> " + ipc.getIpcSectionCode() + ipc.getIpcClassCode() + ipc.getIpcSubclassCode() + ipc.getIpcGroupCode() + ipc.getIpcSubgroupCode());
                ipcRepository.updateEditionAndLatestVersion(ipc.getNewIpcEditionCode(), ipc.getNewIpcLatestVersion(), ipc.getNewIpcName(), ipc.getIpcEditionCode(), ipc.getIpcSectionCode(), ipc.getIpcClassCode(), ipc.getIpcSubclassCode(), ipc.getIpcGroupCode(), ipc.getIpcSubgroupCode());
            }
        }
        log.info("################# updateEditionOfValidIpcClasses Completed! #################");
    }

    @Override
    public void fixInvalidNames() {
        log.info("################# fixInvalidNames Started! #################");
        List<IPCEntityPK> invalidPKs = ipcRepository.selectIpcsWithInvalidNames();
        int i = 0;
        for (IPCEntityPK pk : invalidPKs) {
            log.info("updating IPC name  " + (i++) + " / " + invalidPKs.size() + " -> " + pk.getIpcSectionCode() + pk.getIpcClassCode() + pk.getIpcSubclassCode() + pk.getIpcGroupCode() + pk.getIpcSubgroupCode());
            IPCLatestEntity ipcByPK = ipcLatestService.getBySymbol(pk);
            if (Objects.nonNull(ipcByPK)) {
                ipcRepository.updateIpcByPK(ipcByPK.getIpcName(), ipcByPK.getIpcEntityPK().getIpcEditionCode(), ipcByPK.getIpcLatestVersion(), pk);
            }
        }
        log.info("################# fix entitiesWithLessThan5CharDifference Started! #################");
        List<IPCWithDifferentEdition> entitiesWithLessThan5CharDifference = ipcRepository.getEntitiesWithLessThan5CharDifference();
        i = 0;
        int size = entitiesWithLessThan5CharDifference.size();
        for (IPCWithDifferentEdition ipc : entitiesWithLessThan5CharDifference) {
            log.info((i++) + " / " + size);
            log.info("updating edition entitiesWithLessThan5CharDifference -> " + ipc.getIpcSectionCode() + ipc.getIpcClassCode() + ipc.getIpcSubclassCode() + ipc.getIpcGroupCode() + ipc.getIpcSubgroupCode());
            ipcRepository.updateEditionAndLatestVersion(ipc.getNewIpcEditionCode(), ipc.getNewIpcLatestVersion(), ipc.getNewIpcName(), ipc.getIpcEditionCode(), ipc.getIpcSectionCode(), ipc.getIpcClassCode(), ipc.getIpcSubclassCode(), ipc.getIpcGroupCode(), ipc.getIpcSubgroupCode());
        }
        log.info("################# fix entitiesWithLessThan5CharDifference Started! #################");
        List<IPCWithDifferentEdition> entitiesWithCutNames = ipcRepository.getEntitiesWithCutNames();
        i = 0;
        size = entitiesWithCutNames.size();
        for (IPCWithDifferentEdition ipc : entitiesWithCutNames) {
            log.info((i++) + " / " + size);
            log.info("updating edition entitiesWithCutNames -> " + ipc.getIpcSectionCode() + ipc.getIpcClassCode() + ipc.getIpcSubclassCode() + ipc.getIpcGroupCode() + ipc.getIpcSubgroupCode());
            ipcRepository.updateEditionAndLatestVersion(ipc.getNewIpcEditionCode(), ipc.getNewIpcLatestVersion(), ipc.getNewIpcName(), ipc.getIpcEditionCode(), ipc.getIpcSectionCode(), ipc.getIpcClassCode(), ipc.getIpcSubclassCode(), ipc.getIpcGroupCode(), ipc.getIpcSubgroupCode());
        }
        log.info("################# fixInvalidNames Completed! #################");
    }

    @Override
    public void addMissingIpcClasses() {
        log.info("################# addMissingIpcClasses Started! #################");
        ipcRepository.insertMissingEntities();
        log.info("################# addMissingIpcClasses Completed! #################");
    }

    @Override
    public void finalizeIpcUpdate() {
        log.info("################# finalizeIpcUpdate Started! #################");
        ipcRepository.setExtConfigIpcParam();
        ipcRepository.setEmptyLatestVersions();
        log.info("################# finalizeIpcUpdate Completed! #################");
    }

    class IPCComparator implements Comparator<IPCClassEntity> {
        @Override
        public int compare(IPCClassEntity first, IPCClassEntity second) {
            return Integer.compare(Integer.parseInt(first.getIpcEntityPK().getIpcEditionCode()), Integer.parseInt(second.getIpcEntityPK().getIpcEditionCode()));
        }
    }

    @Override
    public List<IpcTableRowDTO> searchMissingClasses(IpcClassesFilterDTO filter) {
        return ipcTableRowMapper.toDtoList(ipcRepositoryCustom.searchMissingClasses(filter));
    }

    @Override
    public int getMissingClassesCount(IpcClassesFilterDTO filter) {
        return ipcRepositoryCustom.getMissingClassesCount(filter);
    }

    @Override
    public List<IpcTableRowDTO> searchOldVersionClasses(IpcClassesFilterDTO filter) {
        return ipcTableRowMapper.toDtoList(ipcRepositoryCustom.searchOldVersionClasses(filter));
    }

    @Override
    public int getOldVersionClassesCount(IpcClassesFilterDTO filter) {
        return ipcRepositoryCustom.getOldVersionClassesCount(filter);
    }

    @Override
    public IpcDTO getById(String symbol, String edition) throws Exception {
        return ipcMapper.toDto(ipcRepository.findById(Utils.symbolAndEditionToPk(symbol, edition)).orElse(null));
    }

    @Override
    public void deleteIpcIfOldAndUnused(String symbol, String oldEdition) throws Exception {
        IPCEntityPK ipcPk = Utils.symbolAndEditionToPk(symbol, oldEdition);
        IPCClassEntity ipcClassEntity = ipcRepository.findById(ipcPk).orElse(null);
        if (Objects.nonNull(ipcClassEntity)) {
            String latestVersionParam = ipcRepositoryCustom.getLatestVersionParam();
            LogDTO logDTO = new LogDTO();
            logDTO.setOperation("Delete old unused IPC");
            logDTO.setResponsibleUser(SecurityUtils.getUsername());
            logDTO.setAffectedTable("CF_CLASS_IPC");
            if (StringUtils.hasText(latestVersionParam)) {
                Integer relatedCount = patentIpcService.selectPatentsIpcCountByIpc(ipcPk);
                if ((!StringUtils.hasText(ipcClassEntity.getIpcLatestVersion()) || !ipcClassEntity.getIpcLatestVersion().equals(latestVersionParam)) && relatedCount == 0) {
                    try {
                        logDTO.setOldData(ipcClassEntity.getIpcEntityPK().toString());
                        ipcRepository.delete(ipcClassEntity);
                        logDTO.setOperationStatus("OK");
                    } catch (Exception e) {
                        logDTO.setOperationStatus("FAIL");
                        logDTO.setNote(e.getMessage());
                        throw e;
                    } finally {
                        logService.createLog(logDTO);
                    }
                }
            } else {
                logDTO.setOperationStatus("FAIL");
                logDTO.setNote("Missing latestVersionParam (EXT_CONFIG_PARAM)");
                logService.createLog(logDTO);
                throw new Exception("Missing latestVersionParam (EXT_CONFIG_PARAM)");
            }
        }
    }

    @Override
    public List<String> ipcAutocomplete(AutocompleteFilterDTO filter) {
        return ipcRepositoryCustom.ipcAutocomplete(filter);
    }
}
