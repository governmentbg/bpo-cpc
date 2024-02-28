package bg.duosoft.bpo.patent_classification.be.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogDTO {
    private String operation;
    private String operationStatus;
    private String affectedTable;
    private String oldData;
    private String newData;
    private String responsibleUser;
    private String note;
}
