package bg.duosoft.bpo.patent_classification.be.service.ipc;

import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCEntityPK;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCLatestEntity;
import jakarta.xml.bind.JAXBException;

public interface IPCLatestService {
    void saveAllXmlEntries() throws JAXBException;

    IPCLatestEntity getBySymbol(IPCEntityPK pk);

    IpcDTO getDtoBySymbol(IPCEntityPK pk);
}
