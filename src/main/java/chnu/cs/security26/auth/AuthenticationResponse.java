package chnu.cs.security26.auth;

/*
    @author     owner
    @project    security26
    @class      AuthenticationResponse
    @version    1.0.0
    @since      22.11.2026 - 0:18.18
*/
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
}
