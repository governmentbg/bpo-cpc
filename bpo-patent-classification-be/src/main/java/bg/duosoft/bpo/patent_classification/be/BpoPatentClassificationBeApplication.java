package bg.duosoft.bpo.patent_classification.be;

import bg.duosoft.bpo.patent_classification.be.util.appinfo.AppInfo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BpoPatentClassificationBeApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BpoPatentClassificationBeApplication.class, args);
        AppInfo.logApplicationInfo(context);
    }
}
