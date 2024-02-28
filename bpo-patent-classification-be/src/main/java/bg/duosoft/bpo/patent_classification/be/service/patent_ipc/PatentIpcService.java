package bg.duosoft.bpo.patent_classification.be.service.patent_ipc;

import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcPatentTableRowDTO;
import bg.duosoft.bpo.patent_classification.be.domain.dto.filter.IpcPatentsFilterDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCEntityPK;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PatentIpcService {

    List<IpcPatentTableRowDTO> searchIpcPatents(IpcPatentsFilterDTO filter) throws Exception;

    int getIpcPatentsCount(IpcPatentsFilterDTO filter) throws Exception;

    void changeEditionForUsedIPC(String symbol, String oldEdition, String newEdition) throws Exception;

    @Transactional(propagation = Propagation.REQUIRED)
    void updateEntries(String ipcSectionCode, String ipcClassCode, String ipcSubclassCode, String ipcGroupCode,
                       String ipcSubgroupCode, String oldIpcEditionCode, String newIpcEditionCode);

    void changeIpcByPatentKey(List<String> ids, String symbol, String oldEdition, String newEdition) throws Exception;

    Integer selectPatentsIpcCountByIpc(IPCEntityPK ipcPk);

    void changeMissingIPC(String oldEdition, String oldSymbol, String newEditionAndSymbol) throws Exception;
}
