package game.pandemic.user;

import game.pandemic.auth.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserController.REQUEST_MAPPING)
@RequiredArgsConstructor
public class UserController {
    public static final String REQUEST_MAPPING = "/user";
    public static final String GET_ACCESS_TOKEN_URL = "/access-token";
    public static final String GET_ACCESS_TOKEN_OAUTH2_URL = GET_ACCESS_TOKEN_URL + "/oauth2";

    public final UserService userService;

    @GetMapping(GET_ACCESS_TOKEN_OAUTH2_URL)
    public ResponseEntity<String> getAccessTokenForRegisteredUser(@AuthenticationPrincipal final Account account) {
        return ResponseEntity.ok(this.userService.getAccessTokenForRegisteredUser(account));
    }

    @GetMapping(GET_ACCESS_TOKEN_URL)
    public ResponseEntity<String> getAccessTokenForGuestUser(@RequestParam final String username) {
        return ResponseEntity.ok(this.userService.getAccessTokenForGuestUser(username));
    }
}
