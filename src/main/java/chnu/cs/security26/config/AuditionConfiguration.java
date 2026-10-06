package chnu.cs.security26.config;

/*
    @author     owner
    @project    security26
    @class      AuditionConfiguration
    @version    1.0.0
    @since      06.10.2026 - 15.17
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@Configuration
public class AuditionConfiguration {
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}
