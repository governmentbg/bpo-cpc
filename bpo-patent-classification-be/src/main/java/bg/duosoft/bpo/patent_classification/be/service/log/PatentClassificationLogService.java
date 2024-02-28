package bg.duosoft.bpo.patent_classification.be.service.log;

import bg.duosoft.bpo.patent_classification.be.domain.dto.LogDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.log.PatentClassificationLogEntity;

public interface PatentClassificationLogService {

    void createLog(LogDTO log);

}
