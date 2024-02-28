package bg.duosoft.bpo.patent_classification.be.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IpcDTO {
    private String ipcEditionCode;
    private String ipcSectionCode;
    private String ipcClassCode;
    private String ipcSubclassCode;
    private String ipcGroupCode;
    private String ipcSubgroupCode;
    private Integer rowVersion;
    private String ipcName;
    private String xmlDesigner;
    private String ipcLatestVersion;
    private String symbol;
}
