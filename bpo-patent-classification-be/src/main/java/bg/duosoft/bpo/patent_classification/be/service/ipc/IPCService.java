package bg.duosoft.bpo.patent_classification.be.service.ipc;

import bg.duosoft.bpo.common.dto.filter.AutocompleteFilterDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcTableRowDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.filter.IpcClassesFilterDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCClassEntity;

import java.util.List;

public interface IPCService {

    List<IPCClassEntity> selectAllWithSameCode(String ipcSectionCode, String ipcClassCode, String ipcSubclassCode,
                                               String ipcGroupCode, String ipcSubgroupCode);

    void deleteUnusedIpcClasses();

    void normalizeRepeatedIpcClasses();

    void takeLatestForDuplicateIpcs();

    void updateEditionOfValidIpcClasses();

    void fixInvalidNames();

    void addMissingIpcClasses();

    void finalizeIpcUpdate();

    List<IpcTableRowDTO> searchMissingClasses(IpcClassesFilterDTO filter);

    int getMissingClassesCount(IpcClassesFilterDTO filter);

    List<IpcTableRowDTO> searchOldVersionClasses(IpcClassesFilterDTO filter);

    int getOldVersionClassesCount(IpcClassesFilterDTO filter);

    IpcDTO getById(String symbol, String edition) throws Exception;

    void deleteIpcIfOldAndUnused(String symbol, String oldEdition) throws Exception;

    List<String> ipcAutocomplete(AutocompleteFilterDTO filter);
}
