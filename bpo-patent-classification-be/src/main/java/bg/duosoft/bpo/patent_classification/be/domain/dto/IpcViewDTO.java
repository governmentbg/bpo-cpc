package bg.duosoft.bpo.patent_classification.be.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IpcViewDTO {
    private IpcDTO currentIpc;
    private IpcDTO latestIpc;
}
