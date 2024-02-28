package bg.duosoft.bpo.patent_classification.be.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class IpcPatentTableRowDTO {
    private String id;
    private Long regNum;
    private String patentTitle;
    private String patentTitleEn;
    private String urlType;
}
