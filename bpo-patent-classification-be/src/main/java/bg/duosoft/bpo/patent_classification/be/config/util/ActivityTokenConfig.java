package bg.duosoft.bpo.patent_classification.be.config.util;

import bg.duosoft.bpo.patent_classification.be.util.ActivityToken;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivityTokenConfig {

    @Bean
    public ActivityToken generationToken() {
        ActivityToken activityToken = new ActivityToken();
        activityToken.setCurrentProcess(null);
        activityToken.setIpcXmlSchemaFileName(null);
        activityToken.setTitleAFileName(null);
        activityToken.setTitleBFileName(null);
        activityToken.setTitleCFileName(null);
        activityToken.setTitleDFileName(null);
        activityToken.setTitleEFileName(null);
        activityToken.setTitleFFileName(null);
        activityToken.setTitleGFileName(null);
        activityToken.setTitleHFileName(null);
        activityToken.setTitleYFileName(null);
        activityToken.setValidityFileName(null);
        activityToken.setConcordanceFileName(null);
        activityToken.setCpcLatestVersion(null);
        activityToken.setCpcProcessHistory(new CircularFifoQueue<>(20));
        activityToken.setIpcProcessHistory(new CircularFifoQueue<>(20));
        return activityToken;
    }

}
