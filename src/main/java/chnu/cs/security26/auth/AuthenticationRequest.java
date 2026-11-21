package chnu.cs.security26.auth;

/*
    @author     owner
    @project    security26
    @class      AuthenticationRequest
    @version    1.0.0
    @since      22.11.2026 - 0:18.18
*/

import lombok.Data;
import lombok.NonNull;
@Data
public class AuthenticationRequest {
    @NonNull
    private String login;
    @NonNull
    private String password;
}