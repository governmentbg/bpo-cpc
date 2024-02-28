package bg.duosoft.bpo.patent_classification.be.mapper;

import bg.duosoft.bpo.common.service.mapper.BaseObjectMapper;
import bg.duosoft.bpo.patent_classification.be.domain.dto.IpcDTO;
import bg.duosoft.bpo.patent_classification.be.domain.entity.ipc.IPCClassEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class IpcMapper extends BaseObjectMapper<IPCClassEntity, IpcDTO> {

    @Mapping(target = "ipcEditionCode", source = "ipcEntityPK.ipcEditionCode")
    @Mapping(target = "ipcSectionCode", source = "ipcEntityPK.ipcSectionCode")
    @Mapping(target = "ipcClassCode", source = "ipcEntityPK.ipcClassCode")
    @Mapping(target = "ipcSubclassCode", source = "ipcEntityPK.ipcSubclassCode")
    @Mapping(target = "ipcGroupCode", source = "ipcEntityPK.ipcGroupCode")
    @Mapping(target = "ipcSubgroupCode", source = "ipcEntityPK.ipcSubgroupCode")
    public abstract IpcDTO toDto(IPCClassEntity ipcClassEntity);

}
