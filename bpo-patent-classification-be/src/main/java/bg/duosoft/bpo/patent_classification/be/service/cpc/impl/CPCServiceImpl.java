package bg.duosoft.bpo.patent_classification.be.service.cpc.impl;

import bg.duosoft.bpo.patent_classification.be.repository.cpc.CPCRepository;
import bg.duosoft.bpo.patent_classification.be.service.cpc.CPCService;
import bg.duosoft.bpo.patent_classification.be.util.cpc.CPCWithDifferentEdition;
import bg.duosoft.bpo.patent_classification.be.util.cpc.NameUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CPCServiceImpl implements CPCService {
    private final CPCRepository cpcRepository;

    @Override
    public void deleteUnusedCpcClasses() {
        log.info("################# deleteUnusedCpcClasses Started! #################");
        cpcRepository.deleteUnusedCpcClasses();
        log.info("################# deleteUnusedCpcClasses Completed! #################");
    }

    @Override
    public void updateValidCpcClasses() {
        log.info("################# updateValidCpcClasses Started! #################");
        List<CPCWithDifferentEdition> entitiesWithDifferentEditionCodes = cpcRepository.getEntitiesWithDifferentEditionCodes();
        int i = 0;
        int size = entitiesWithDifferentEditionCodes.size();
        for (CPCWithDifferentEdition cpc : entitiesWithDifferentEditionCodes) {
            log.info((i++) + " / " + size);
            if (NameUtil.cpcNameEquals(cpc.getCpcName(), cpc.getNewCpcName())) {
                log.info("updating -> " + cpc.getCpcSectionCode() + cpc.getCpcClassCode() + cpc.getCpcSubclassCode() + cpc.getCpcGroupCode() + cpc.getCpcSubgroupCode());
                cpcRepository.updateEditionAndLatestVersion(cpc.getNewCpcEditionCode(), cpc.getNewCpcLatestVersion(), cpc.getNewCpcName(), cpc.getCpcEditionCode(), cpc.getCpcSectionCode(), cpc.getCpcClassCode(), cpc.getCpcSubclassCode(), cpc.getCpcGroupCode(), cpc.getCpcSubgroupCode());
            }
        }
        log.info("################# updateValidCpcClasses Completed! #################");
    }

    @Override
    public void addMissingCpcClasses() {
        log.info("################# addMissingCpcClasses Started! #################");
        cpcRepository.insertMissingEntities();
        log.info("################# addMissingCpcClasses Completed! #################");
    }

    @Override
    public void finalizeCpcUpdate(String latestVersion) {
        log.info("################# finalizeCpcUpdate Started! #################");
        cpcRepository.setExtConfigCpcParam(latestVersion);
        log.info("################# finalizeCpcUpdate Completed! #################");
    }
}
