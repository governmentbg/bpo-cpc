package bg.duosoft.bpo.patent_classification.be.util.appinfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.util.Objects;

@Slf4j
public class AppInfo {

    public static void logApplicationInfo(ConfigurableApplicationContext run) {
        ConfigurableEnvironment env = run.getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }

        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "External: \t{}://{}:{}{}\n\t" +
                        "Memory: \t{}\n\t" +
                        "Profile(s): \t{}\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                env.getProperty("server.port"),
                Objects.isNull(env.getProperty("server.servlet.context-path")) ? "" : env.getProperty("server.servlet.context-path"),
                protocol,
                hostAddress,
                env.getProperty("server.port"),
                Objects.isNull(env.getProperty("server.servlet.context-path")) ? "" : env.getProperty("server.servlet.context-path"),
                Memory.getInfo().toString(),
                env.getActiveProfiles()
        );
    }

}
