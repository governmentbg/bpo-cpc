package bg.duosoft.bpo.patent_classification.be.util;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.queue.CircularFifoQueue;

@Getter
@Setter
public class ActivityToken {
    private String currentProcess;
    private String ipcXmlSchemaFileName;
    private String titleAFileName;
    private String titleBFileName;
    private String titleCFileName;
    private String titleDFileName;
    private String titleEFileName;
    private String titleFFileName;
    private String titleGFileName;
    private String titleHFileName;
    private String titleYFileName;
    private String concordanceFileName;
    private String validityFileName;
    private String cpcLatestVersion;
    private CircularFifoQueue<Activity> ipcProcessHistory;
    private CircularFifoQueue<Activity> cpcProcessHistory;
}
