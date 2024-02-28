package bg.duosoft.bpo.patent_classification.be.mapper;

import bg.duosoft.bpo.common.service.mapper.BaseObjectMapper;
import bg.duosoft.bpo.patent_classification.be.domain.dto.LogDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.log.PatentClassificationLogEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class PatentClassificationLogMapper extends BaseObjectMapper<PatentClassificationLogEntity, LogDTO> {

}
