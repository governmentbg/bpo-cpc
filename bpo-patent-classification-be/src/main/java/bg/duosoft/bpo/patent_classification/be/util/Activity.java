package bg.duosoft.bpo.patent_classification.be.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Activity {
    private String processName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String user;
    private String status;
}
