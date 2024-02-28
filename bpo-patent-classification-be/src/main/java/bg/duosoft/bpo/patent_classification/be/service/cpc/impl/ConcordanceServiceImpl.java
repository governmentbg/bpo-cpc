package bg.duosoft.bpo.patent_classification.be.service.cpc.impl;

import bg.duosoft.bpo.patent_classification.be.domain.entity.concordance.ConcordanceEntity;
import bg.duosoft.bpo.patent_classification.be.domain.entity.concordance.ConcordanceEntityPK;
import bg.duosoft.bpo.patent_classification.be.repository.cpc.ConcordanceRepository;
import bg.duosoft.bpo.patent_classification.be.service.cpc.ConcordanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static bg.duosoft.bpo.patent_classification.be.util.cpc.CpcFilesEnum.CONCORDANCE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConcordanceServiceImpl implements ConcordanceService {
    private final ConcordanceRepository repository;

    @Value("${cpc.files.location}")
    private String cpcFilesLocation;

    @Override
    public void fillConcordanceTable(String latestVersion) throws Exception {
        log.info("################# deleteAllConcordanceTable Started! #################");
        repository.clearConcordance();
        log.info("################# deleteAllConcordanceTable Completed! #################");
        log.info("################# fillConcordanceTable Started! #################");
        File titleTxt = new File(cpcFilesLocation + CONCORDANCE.getValue());
        Scanner scanner = new Scanner(titleTxt);
        List<ConcordanceEntity> concordances = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] split = line.split("\t\t");
            if (split.length != 3) {
                throw new RuntimeException("Incorrect format of concordance file!");
            }

            ConcordanceEntity concordanceEntity = new ConcordanceEntity();
            concordanceEntity.setPk(new ConcordanceEntityPK());

            String cpcSymbol = split[0].trim();
            String ipcSymbol = split[1].trim();
            if (!cpcSymbol.equals("CPC Group") && !ipcSymbol.equals("IPC Group") && !ipcSymbol.equals("CPCONLY")) {
                try {
                    concordanceEntity.getPk().setCpcSectionCode(cpcSymbol.substring(0, 1));
                    concordanceEntity.getPk().setCpcClassCode(cpcSymbol.substring(1, 3));
                    concordanceEntity.getPk().setCpcSubclassCode(cpcSymbol.substring(3, 4));
                    String[] cpcGroupAndSubgroup = cpcSymbol.substring(4).split("/");
                    concordanceEntity.getPk().setCpcGroupCode(cpcGroupAndSubgroup[0]);
                    concordanceEntity.getPk().setCpcSubgroupCode(cpcGroupAndSubgroup[1]);
                    concordanceEntity.setCpcSymbol(cpcSymbol);

                    concordanceEntity.setIpcSectionCode(ipcSymbol.substring(0, 1));
                    concordanceEntity.setIpcClassCode(ipcSymbol.substring(1, 3));
                    concordanceEntity.setIpcSubclassCode(ipcSymbol.substring(3, 4));
                    String[] ipcGroupAndSubgroup = ipcSymbol.substring(4).split("/");
                    concordanceEntity.setIpcGroupCode(ipcGroupAndSubgroup[0]);
                    concordanceEntity.setIpcSubgroupCode(ipcGroupAndSubgroup[1]);
                    concordanceEntity.setIpcSymbol(ipcSymbol);
                    concordanceEntity.setLatestVersion(latestVersion);
                    concordances.add(concordanceEntity);

                } catch (Exception e) {
                    throw new RuntimeException("Error converting concordance for " + cpcSymbol + " / " + ipcSymbol);
                }
            }
        }
        repository.saveAll(concordances);
        log.info("################# fillConcordanceTable Completed! #################");
    }
}
