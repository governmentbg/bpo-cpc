package bg.duosoft.bpo.patent_classification.be.repository.patent_ipc;

import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCEntityPK;
import bg.duosoft.bpo.patent_classification.be.domain.entity.patent_ipc.PatentIpcEntity;
import bg.duosoft.bpo.patent_classification.be.domain.entity.patent_ipc.PatentIpcEntityPK;
import bg.duosoft.bpo.patent_classification.be.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatentIPCRepository extends BaseRepository<PatentIpcEntity, PatentIpcEntityPK> {
    @Query("select count(c) from PatentIpcEntity c where c.pk.ipc.ipcEntityPK = :ipcPk")
    Integer selectPatentsIpcCountByIpc(IPCEntityPK ipcPk);

    @Query("select c from PatentIpcEntity c where c.pk.ipc.ipcEntityPK = :ipcPk")
    List<PatentIpcEntity> selectPatentsIpcByIpc(IPCEntityPK ipcPk);

}
