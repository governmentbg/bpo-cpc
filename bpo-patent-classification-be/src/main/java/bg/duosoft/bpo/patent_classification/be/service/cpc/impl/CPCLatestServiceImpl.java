package bg.duosoft.bpo.patent_classification.be.service.cpc.impl;

import bg.duosoft.bpo.patent_classification.be.domain.entity.cpc.CPCEntityPK;
import bg.duosoft.bpo.patent_classification.be.domain.entity.cpc.CPCLatestEntity;
import bg.duosoft.bpo.patent_classification.be.repository.cpc.CPCLatestRepository;
import bg.duosoft.bpo.patent_classification.be.service.cpc.CPCLatestService;
import bg.duosoft.bpo.patent_classification.be.util.Utils;
import bg.duosoft.bpo.patent_classification.be.util.cpc.CpcFilesEnum;
import bg.duosoft.bpo.patent_classification.be.util.cpc.ValidityPair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CPCLatestServiceImpl implements CPCLatestService {

    private final CPCLatestRepository cpcLatestRepository;

    @Value("${cpc.files.location}")
    private String cpcFilesLocation;

    @Override
    public void saveNewCpcEntries(String latestVersion) throws FileNotFoundException {
        log.info("===================== Saving New CPC Entries =====================");
        log.info("+++++++++++++++++++++ Clearing CF_CLASS_CPC_LATEST_SPEC +++++++++++++++++++++");
        cpcLatestRepository.clearLatestSpec();
        log.info("+++++++++++++++++++++ Clearing CF_CLASS_CPC_LATEST_SPEC Completed +++++++++++++++++++++");

        log.info("+++++++++++++++++++++ Loading Validity Map +++++++++++++++++++++");
        File validityFile = new File(cpcFilesLocation + "validity.txt");
        Scanner scanner = new Scanner(validityFile);
        List<String> validityLines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            validityLines.add(scanner.nextLine());
        }
        Map<String, ValidityPair> validityMap = new HashMap<>();
        for (String validityLine : validityLines) {
            String[] split = validityLine.split("\t");

            if (split.length == 2) {
                String code = split[0].trim();
                String validFrom = split[1].trim().replace("-", "");
                validityMap.put(code, new ValidityPair(validFrom, null));
            } else if (split.length == 3) {
                String code = split[0].trim();
                String validFrom = split[1].trim().replace("-", "");
                String validTo = split[2].trim().replace("-", "");
                ValidityPair validityPair = new ValidityPair(validFrom, validTo);
                validityMap.put(code, validityPair);
            }
        }
        log.info("+++++++++++++++++++++ Loading Validity Map Completed +++++++++++++++++++++");

        for (CpcFilesEnum titleFile : CpcFilesEnum.getAllTitleTxts()) {
            String titleCode = titleFile.getCode();
            log.info("+++++++++++++++++++++ Saving Section " + titleCode + " +++++++++++++++++++++");
            File titleTxt = new File(cpcFilesLocation + titleFile.getValue());
            scanner = new Scanner(titleTxt);
            List<String> lines = new ArrayList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            int i = 0;
            for (String line : lines) {
                String[] split = line.split("\t");
                if (split.length != 3) {
                    throw new RuntimeException("Incorrect format in " + titleTxt.getName());
                }
                String code = split[0].trim();
                String dots = split[1].trim();
                String name = split[2].trim();

                if (!StringUtils.hasText(code) || !StringUtils.hasText(name)) {
                    throw new RuntimeException("Incorrect code format in " + titleTxt.getName());
                }
                if (!code.substring(0, 1).equals(titleCode)) {
                    throw new RuntimeException("Invalid content in " + titleTxt.getName() + ": " + code);
                }

                StringBuilder finalName = new StringBuilder();
                if (Utils.isNumeric(dots)) {
                    int d = Integer.parseInt(dots);
                    for (int x = 0; x < d; x++) {
                        finalName.append(". ");
                    }
                }
                finalName.append(name);
                if (code.length() >= 4) {
                    CPCEntityPK pk = new CPCEntityPK();
                    pk.setCpcSectionCode(code.substring(0, 1));
                    pk.setCpcClassCode(code.substring(1, 3));
                    pk.setCpcSubclassCode(code.substring(3, 4));
                    if (code.length() == 4) {
                        pk.setCpcGroupCode("0");
                        pk.setCpcSubgroupCode("0");
                    } else {
                        String[] groupAndSubgroup = code.substring(4).split("/");
                        if (groupAndSubgroup.length != 2) {
                            throw new RuntimeException("Incorrect code format in " + titleTxt.getName());
                        }
                        pk.setCpcGroupCode(groupAndSubgroup[0]);
                        pk.setCpcSubgroupCode(groupAndSubgroup[1]);
                    }

                    ValidityPair validityPair = validityMap.get(code);
                    pk.setCpcEditionCode(Objects.nonNull(validityPair) ? validityPair.getValidFrom() : "EMPTY");

                    CPCLatestEntity cpcLatestEntity = new CPCLatestEntity();
                    cpcLatestEntity.setCpcEntityPK(pk);
                    cpcLatestEntity.setCpcName(finalName.toString());
                    cpcLatestEntity.setRowVersion(1);
                    cpcLatestEntity.setSymbol(code);
                    cpcLatestEntity.setCpcLatestVersion(latestVersion);

                    if (!StringUtils.hasText(cpcLatestEntity.getCpcName())) {
                        System.out.println(1);
                    }
                    cpcLatestRepository.save(cpcLatestEntity);
                }
                log.info("Saving " + titleTxt.getName() + " -> " + i++ + " / " + lines.size() + " -> " + code);
            }
            log.info("+++++++++++++++++++++ Saving Section " + titleCode + " Completed! +++++++++++++++++++++");
        }
        log.info("===================== Saving New CPC Entries Completed =====================");
    }


}
