package bg.duosoft.bpo.patent_classification.be.mapper;

import bg.duosoft.bpo.common.service.mapper.BaseObjectMapper;
import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcPatentTableRowDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.patent_ipc.PatentIpcEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class IpcPatentTableRowMapper extends BaseObjectMapper<PatentIpcEntity, IpcPatentTableRowDTO> {

    @Mapping(target = "regNum", source = "pk.patent.registrationNbr")
    @Mapping(target = "patentTitle", source = "pk.patent.title")
    @Mapping(target = "patentTitleEn", source = "pk.patent.englishTitle")
    public abstract IpcPatentTableRowDTO toDto(PatentIpcEntity patentIpcEntity);

    @AfterMapping
    protected void afterToDTO(PatentIpcEntity source, @MappingTarget IpcPatentTableRowDTO target) {
        target.setId(source.getPk().getPatent().getPk().getFileSeq() + "/" + source.getPk().getPatent().getPk().getFileTyp() + "/" + source.getPk().getPatent().getPk().getFileSer() + "/" + source.getPk().getPatent().getPk().getFileNbr());
        switch (source.getPk().getPatent().getPk().getFileTyp()) {
            case "P" -> target.setUrlType("patent");
            case "U" -> target.setUrlType("utility_model");
            case "T" -> target.setUrlType("eupatent");
        }
    }
}
