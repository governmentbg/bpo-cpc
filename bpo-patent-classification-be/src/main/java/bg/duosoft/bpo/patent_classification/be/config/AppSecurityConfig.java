package bg.duosoft.bpo.patent_classification.be.config;

import bg.duosoft.bpo.common.security.config.JwtSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig {

    @Configuration
    @Import(JwtSecurityConfig.class)
    public static class SecurityConfig {
    }
}
