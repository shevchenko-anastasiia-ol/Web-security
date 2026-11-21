package chnu.cs.security26.auth;

/*
    @author     owner
    @project    security26
    @class      AuthenticationController
    @version    1.0.0
    @since      22.11.2026 - 0:17.17
*/

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("'/authenticate")
    public ResponseEntity<AuthenticationResponse>
    authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request)) ;
    }


}