package outsera.goldenraspberry.bootstrap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import outsera.goldenraspberry.domain.service.AwardIntervalCalculator;

@Configuration
public class BeanConfig {

    @Bean
    AwardIntervalCalculator awardIntervalCalculator() {
        return new AwardIntervalCalculator();
    }
}
