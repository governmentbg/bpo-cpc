package bg.duosoft.bpo.patent_classification.be.service.ipc.impl;

import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCEntityPK;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCLatestEntity;
import bg.duosoft.bpo.patent_classification.be.jaxb.IPCScheme;
import bg.duosoft.bpo.patent_classification.be.jaxb.ObjectFactory;
import bg.duosoft.bpo.patent_classification.be.mapper.IpcLatestMapper;
import bg.duosoft.bpo.patent_classification.be.repository.ipc.IPCLatestRepository;
import bg.duosoft.bpo.patent_classification.be.repository.ipc.IPCLatestRepositoryCustom;
import bg.duosoft.bpo.patent_classification.be.service.ipc.IPCLatestService;
import bg.duosoft.bpo.patent_classification.be.util.ipc.JAXBtoEntityConverter;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IPCLatestServiceImpl implements IPCLatestService {

    private final IPCLatestRepository ipcLatestRepository;

    private final IPCLatestRepositoryCustom ipcLatestRepositoryCustom;
    private final IpcLatestMapper ipcLatestMapper;

    @Value("${xml.schema.file.location}")
    private String xmlFileLocation;


    @Override
    public void saveAllXmlEntries() throws JAXBException {
        File xmlFile = new File(xmlFileLocation);
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement<IPCScheme> ipcEntryAttributes = (JAXBElement<IPCScheme>) jaxbUnmarshaller.unmarshal(xmlFile);
            List<IPCLatestEntity> ipcEntities = JAXBtoEntityConverter.convertToEntity(ipcEntryAttributes);
            log.info("################# Clearing CF_CLASS_IPC_LATEST_SPEC! #################");
            ipcLatestRepositoryCustom.deleteAll();
            log.info("################# saveAllXmlEntries Started! #################");
            ipcLatestRepository.saveAll(ipcEntities);
        } catch (JAXBException e) {
            e.printStackTrace();
            throw e;
        }
        log.info("################# saveAllXmlEntries Completed! #################");
    }

    @Override
    public IPCLatestEntity getBySymbol(IPCEntityPK pk) {
        List<IPCLatestEntity> ipcLatestEntities = ipcLatestRepository.selectByCode(pk.getIpcSectionCode(), pk.getIpcClassCode(), pk.getIpcSubclassCode(), pk.getIpcGroupCode(), pk.getIpcSubgroupCode());
        if (CollectionUtils.isEmpty(ipcLatestEntities)) {
            return null;
        } else {
            return ipcLatestEntities.get(0);
        }
    }

    @Override
    public IpcDTO getDtoBySymbol(IPCEntityPK pk) {
        return ipcLatestMapper.toDto(getBySymbol(pk));
    }
}
