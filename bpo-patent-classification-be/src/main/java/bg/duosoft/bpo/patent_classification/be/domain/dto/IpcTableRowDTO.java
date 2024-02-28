package bg.duosoft.bpo.patent_classification.be.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IpcTableRowDTO {
    private String symbol;
    private String ipcName;
    private String edition;
    private String ipcLatestVersion;
}
