package bg.duosoft.bpo.patent_classification.be.service.patent_ipc.impl;

import bg.duosoft.bpo.common.security.util.SecurityUtils;
import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcPatentTableRowDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.LogDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.filter.IpcPatentsFilterDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCClassEntity;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCEntityPK;
import bg.duosoft.bpo.patent_classification.be.domain.entity.patent_ipc.PatentIpcEntity;
import bg.duosoft.bpo.patent_classification.be.mapper.IpcPatentTableRowMapper;
import bg.duosoft.bpo.patent_classification.be.repository.ipc.IPCRepository;
import bg.duosoft.bpo.patent_classification.be.repository.patent_ipc.PatentIPCRepository;
import bg.duosoft.bpo.patent_classification.be.repository.patent_ipc.PatentIPCRepositoryCustom;
import bg.duosoft.bpo.patent_classification.be.service.log.PatentClassificationLogService;
import bg.duosoft.bpo.patent_classification.be.service.patent_ipc.PatentIpcService;
import bg.duosoft.bpo.patent_classification.be.util.Utils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatentIpcServiceImpl implements PatentIpcService {
    private final PatentIPCRepositoryCustom patentIPCRepositoryCustom;
    private final PatentIPCRepository patentIPCRepository;
    private final IPCRepository ipcRepository;
    private final IpcPatentTableRowMapper ipcPatentTableRowMapper;
    private final PatentClassificationLogService logService;

    @Override
    public List<IpcPatentTableRowDTO> searchIpcPatents(IpcPatentsFilterDTO filter) {
        return ipcPatentTableRowMapper.toDtoList(patentIPCRepositoryCustom.searchPatentIpcClasses(filter));
    }

    @Override
    public int getIpcPatentsCount(IpcPatentsFilterDTO filter) {
        return patentIPCRepositoryCustom.getPatentIpcClassesCount(filter);
    }

    @Override
    @Transactional
    public void changeEditionForUsedIPC(String symbol, String oldEdition, String newEdition) throws Exception {

        LogDTO logDTO = new LogDTO();
        logDTO.setOperation("Classify all patents");
        logDTO.setResponsibleUser(SecurityUtils.getUsername());
        logDTO.setAffectedTable("IP_PATENT_IPC_CLASSES");

        try {
            if (!StringUtils.hasText(newEdition)) {
                throw new Exception("Missing parameter newEdition!");
            }
            IPCEntityPK pk = Utils.symbolAndEditionToPk(symbol, oldEdition);
            ObjectMapper objectMapper = new ObjectMapper(); //for deep copy
            List<PatentIpcEntity> patentIpcEntitiesOriginal = patentIPCRepository.selectPatentsIpcByIpc(pk);
            String json = objectMapper.writeValueAsString(patentIpcEntitiesOriginal);
            List<PatentIpcEntity> patentIpcEntitiesChanged = objectMapper.readValue(json, new TypeReference<>() {});
            logDTO.setOldData(patentIpcEntitiesOriginal.toString());
            for (PatentIpcEntity pt : patentIpcEntitiesChanged) {
                pt.getPk().getIpc().getIpcEntityPK().setIpcEditionCode(newEdition);
            }
            patentIPCRepository.deleteAll(patentIpcEntitiesOriginal);
            patentIPCRepository.saveAll(patentIpcEntitiesChanged);
            logDTO.setNewData(patentIpcEntitiesChanged.toString());
            logDTO.setOperationStatus("OK");
            logDTO.setNote(patentIpcEntitiesChanged.size() + " rows affected");
        } catch (Exception e) {
            logDTO.setOperationStatus("FAIL");
            logDTO.setNote(e.getMessage());
            throw e;
        } finally {
            logService.createLog(logDTO);
        }
    }

    @Override
    public void updateEntries(String ipcSectionCode, String ipcClassCode, String ipcSubclassCode, String ipcGroupCode,
                              String ipcSubgroupCode, String oldIpcEditionCode, String newIpcEditionCode) {
        patentIPCRepositoryCustom.updateEditionOfUsedIpc(ipcSectionCode, ipcClassCode, ipcSubclassCode, ipcGroupCode,
                ipcSubgroupCode, oldIpcEditionCode, newIpcEditionCode);
    }

    @Override
    public Integer selectPatentsIpcCountByIpc(IPCEntityPK ipcPk) {
        Integer count = patentIPCRepository.selectPatentsIpcCountByIpc(ipcPk);
        return Objects.nonNull(count) ? count : 0;
    }

    @Override
    @Transactional
    public void changeIpcByPatentKey(List<String> ids, String symbol, String oldEdition, String newEdition) throws Exception {

        LogDTO logDTO = new LogDTO();
        logDTO.setOperation("Classify multiple patents");
        logDTO.setResponsibleUser(SecurityUtils.getUsername());
        logDTO.setAffectedTable("IP_PATENT_IPC_CLASSES");

        try {
            if (!StringUtils.hasText(newEdition)) {
                throw new Exception("Missing parameter newEdition!");
            }
            IPCEntityPK pk = Utils.symbolAndEditionToPk(symbol, oldEdition);
            ObjectMapper objectMapper = new ObjectMapper(); //for deep copy
            List<PatentIpcEntity> patentIpcEntitiesOriginal = patentIPCRepositoryCustom.selectByPatentKeyAndIpc(ids, pk);
            String json = objectMapper.writeValueAsString(patentIpcEntitiesOriginal);
            List<PatentIpcEntity> patentIpcEntitiesChanged = objectMapper.readValue(json, new TypeReference<>() {
            });
            logDTO.setOldData(patentIpcEntitiesOriginal.toString());
            for (PatentIpcEntity e : patentIpcEntitiesChanged) {
                e.getPk().getIpc().getIpcEntityPK().setIpcEditionCode(newEdition);
            }
            patentIPCRepository.deleteAll(patentIpcEntitiesOriginal);
            patentIPCRepository.saveAll(patentIpcEntitiesChanged);
            logDTO.setNewData(patentIpcEntitiesChanged.toString());
            logDTO.setNote(patentIpcEntitiesChanged.size() + " rows affected");
            logDTO.setOperationStatus("OK");
        } catch (Exception e) {
            logDTO.setOperationStatus("FAIL");
            logDTO.setNote(e.getMessage());
            throw e;
        } finally {
            logService.createLog(logDTO);
        }
    }

    @Override
    public void changeMissingIPC(String oldEdition, String oldSymbol, String newEditionAndSymbol) throws Exception {

        LogDTO logDTO = new LogDTO();
        logDTO.setOperation("Classify all patents with missing latest IPC");
        logDTO.setResponsibleUser(SecurityUtils.getUsername());
        logDTO.setAffectedTable("IP_PATENT_IPC_CLASSES");

        try {
            String[] s = newEditionAndSymbol.split(" ");
            String newSymbol = s[1].trim();
            String newEdition = s[0].replace("(", "").replace(")", "");

            IPCEntityPK oldPk = Utils.symbolAndEditionToPk(oldSymbol, oldEdition);
            IPCEntityPK newPk = Utils.symbolAndEditionToPk(newSymbol, newEdition);
            Optional<IPCClassEntity> ipcExisting = ipcRepository.findById(newPk);
            if (ipcExisting.isEmpty()) {
                throw new Exception("IPC with such edition and symbol does not exist! " + newPk);
            }

            ObjectMapper objectMapper = new ObjectMapper(); //for deep copy
            List<PatentIpcEntity> patentIpcEntitiesOriginal = patentIPCRepository.selectPatentsIpcByIpc(oldPk);
            String json = objectMapper.writeValueAsString(patentIpcEntitiesOriginal);
            List<PatentIpcEntity> patentIpcEntitiesChanged = objectMapper.readValue(json, new TypeReference<>() {});
            logDTO.setOldData(patentIpcEntitiesOriginal.toString());
            for (PatentIpcEntity pt : patentIpcEntitiesChanged) {
                pt.getPk().getIpc().setIpcEntityPK(newPk);
            }
            patentIPCRepository.deleteAll(patentIpcEntitiesOriginal);
            patentIPCRepository.saveAll(patentIpcEntitiesChanged);
            logDTO.setNewData(patentIpcEntitiesChanged.toString());
            logDTO.setOperationStatus("OK");
            logDTO.setNote(patentIpcEntitiesChanged.size() + " rows affected");
        } catch (Exception e) {
            logDTO.setOperationStatus("FAIL");
            logDTO.setNote(e.getMessage());
            throw e;
        } finally {
            logService.createLog(logDTO);
        }
    }
}
