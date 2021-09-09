package nl.schutrup.cerina.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CerinaConfig implements WebMvcConfigurer {
    private final Logger logger = LoggerFactory.getLogger(CerinaConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        try {
            registry
                    .addResourceHandler("/static/bundle/**")
                    .addResourceLocations("file://" + new File("./target/deploy/static/bundle").getCanonicalPath() + "/");
        } catch (IOException e) {
            logger.error("Fout opgetreden" + e.getMessage(), e);
            logger.error("test");
        }
    }

    @Bean
    public Map<String, String> tokenToSession(){
        Map<String, String> map = new HashMap<>();
        return map;
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }


}
