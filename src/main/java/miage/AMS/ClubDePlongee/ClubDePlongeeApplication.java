package miage.AMS.ClubDePlongee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

// Permet de désactiver la page de login de spring security
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) 
@EnableScheduling
@EnableDiscoveryClient
public class ClubDePlongeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubDePlongeeApplication.class, args);
	}

}
