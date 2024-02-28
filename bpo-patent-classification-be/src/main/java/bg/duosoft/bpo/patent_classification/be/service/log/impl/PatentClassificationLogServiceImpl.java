package bg.duosoft.bpo.patent_classification.be.service.log.impl;

import bg.duosoft.bpo.patent_classification.be.domain.dto.LogDTO;
import bg.duosoft.bpo.patent_classification.be.mapper.PatentClassificationLogMapper;
import bg.duosoft.bpo.patent_classification.be.repository.log.PatentClassificationLogRepository;
import bg.duosoft.bpo.patent_classification.be.service.log.PatentClassificationLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatentClassificationLogServiceImpl implements PatentClassificationLogService {
    private final PatentClassificationLogRepository repository;
    private final PatentClassificationLogMapper mapper;

    @Override
    public void createLog(LogDTO log) {
        repository.save(mapper.toEntity(log));
    }
}
