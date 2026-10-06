package chnu.cs.security26.config;

/*
    @author     owner
    @project    security26
    @class      AuditorAwareImpl
    @version    1.0.0
    @since      06.10.2026 - 15.13
*/

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor(){
        return Optional.of(System.getProperty("user.name"));
    }
}
