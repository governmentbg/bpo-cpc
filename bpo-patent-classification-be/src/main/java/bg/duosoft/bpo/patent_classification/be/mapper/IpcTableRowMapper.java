package bg.duosoft.bpo.patent_classification.be.mapper;

import bg.duosoft.bpo.common.service.mapper.BaseObjectMapper;
import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcTableRowDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCClassEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class IpcTableRowMapper extends BaseObjectMapper<IPCClassEntity, IpcTableRowDTO> {

    @Mapping(target = "edition", source = "ipcEntityPK.ipcEditionCode")
    public abstract IpcTableRowDTO toDto(IPCClassEntity ipcClassEntity);

    @AfterMapping
    protected void afterToDTO(IPCClassEntity source, @MappingTarget IpcTableRowDTO target) {
        target.setSymbol(source.getIpcEntityPK().getIpcSectionCode() + source.getIpcEntityPK().getIpcClassCode() + source.getIpcEntityPK().getIpcSubclassCode() + source.getIpcEntityPK().getIpcGroupCode() + "/" + source.getIpcEntityPK().getIpcSubgroupCode());
    }
}
