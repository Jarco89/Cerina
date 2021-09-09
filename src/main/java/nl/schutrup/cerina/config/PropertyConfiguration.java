package nl.schutrup.cerina.config;


import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@PropertySource("classpath:/application.properties")
public class PropertyConfiguration {
}
