package game.pandemic.user;

import game.pandemic.auth.Account;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserController.REQUEST_MAPPING)
@RequiredArgsConstructor
@Validated
public class UserController {
    public static final String REQUEST_MAPPING = "/user";
    public static final String GET_ACCESS_TOKEN_URL = "/access-token";
    public static final String GET_ACCESS_TOKEN_OAUTH2_URL = GET_ACCESS_TOKEN_URL + "/oauth2";

    public static final String BLANK_USERNAME_VALIDATION_EXCEPTION_MESSAGE = "Username may not be blank";

    public final UserService userService;

    @GetMapping(GET_ACCESS_TOKEN_OAUTH2_URL)
    public ResponseEntity<String> getAccessTokenForRegisteredUser(@AuthenticationPrincipal final Account account) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.getAccessTokenForRegisteredUser(account));
    }

    @GetMapping(GET_ACCESS_TOKEN_URL)
    public ResponseEntity<String> getAccessTokenForGuestUser(@RequestParam @NotBlank(message = BLANK_USERNAME_VALIDATION_EXCEPTION_MESSAGE) final String username) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.getAccessTokenForGuestUser(username));
    }
}
