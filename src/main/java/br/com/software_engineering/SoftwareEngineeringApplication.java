package br.com.software_engineering;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SoftwareEngineeringApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure()
					.ignoreIfMissing()
					.load();

		dotenv.entries().forEach(e ->
				System.setProperty(e.getKey(), e.getValue())
		);

		SpringApplication.run(SoftwareEngineeringApplication.class, args);
	}

}
