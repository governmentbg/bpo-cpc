package bg.duosoft.bpo.patent_classification.be.domain.dto.filter;

import bg.duosoft.bpo.common.dto.filter.BaseFilterDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IpcPatentsFilterDTO extends BaseFilterDTO {
    private String symbol;
    private String ipcEditionCode;
    private String ipcSectionCode;
    private String ipcClassCode;
    private String ipcSubclassCode;
    private String ipcGroupCode;
    private String ipcSubgroupCode;
    private String fileSeq;
    private String fileTyp;
    private String fileSer;
    private String fileNbr;
    private String regNum;
    private String patentTitle;
    private String patentTitleEn;
}
