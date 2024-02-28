package bg.duosoft.bpo.patent_classification.be.config;

import bg.duosoft.bpo.common.web.config.CorsOriginsConfig;
import bg.duosoft.bpo.common.web.property.CorsProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CorsProperties.class, CorsOriginsConfig.class})
public class CorsConfig {
}