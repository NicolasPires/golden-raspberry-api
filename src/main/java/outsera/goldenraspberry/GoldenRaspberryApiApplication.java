package outsera.goldenraspberry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GoldenRaspberryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldenRaspberryApiApplication.class, args);
	}

}
