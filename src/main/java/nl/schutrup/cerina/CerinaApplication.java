package nl.schutrup.cerina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (scanBasePackages = "nl.schutrup.cerina")
public class CerinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CerinaApplication.class, args);
	}

}
