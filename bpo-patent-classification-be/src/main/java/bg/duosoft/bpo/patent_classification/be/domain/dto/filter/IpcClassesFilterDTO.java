package bg.duosoft.bpo.patent_classification.be.domain.dto.filter;

import bg.duosoft.bpo.common.dto.filter.BaseFilterDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IpcClassesFilterDTO extends BaseFilterDTO {
    private String ipcEditionCode;
    private String ipcSectionCode;
    private String ipcClassCode;
    private String ipcSubclassCode;
    private String ipcGroupCode;
    private String ipcSubgroupCode;
    private String ipcName;
    private String ipcLatestVersion;
}
