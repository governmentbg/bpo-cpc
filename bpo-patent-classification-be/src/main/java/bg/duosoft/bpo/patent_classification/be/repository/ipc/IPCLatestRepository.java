package bg.duosoft.bpo.patent_classification.be.repository.ipc;

import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCEntityPK;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCLatestEntity;
import bg.duosoft.bpo.patent_classification.be.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPCLatestRepository extends BaseRepository<IPCLatestEntity, IPCEntityPK> {
    @Query("select c from IPCLatestEntity c where c.ipcEntityPK.ipcSectionCode = :ipcSectionCode and " +
            "c.ipcEntityPK.ipcClassCode = :ipcClassCode and " +
            "c.ipcEntityPK.ipcSubclassCode = :ipcSubclassCode and " +
            "c.ipcEntityPK.ipcGroupCode = :ipcGroupCode and " +
            "c.ipcEntityPK.ipcSubgroupCode = :ipcSubgroupCode")
    List<IPCLatestEntity> selectByCode(String ipcSectionCode, String ipcClassCode, String ipcSubclassCode, String ipcGroupCode, String ipcSubgroupCode);

}
