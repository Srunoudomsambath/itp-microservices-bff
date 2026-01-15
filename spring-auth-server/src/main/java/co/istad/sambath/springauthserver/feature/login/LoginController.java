package co.istad.sambath.springauthserver.feature.login;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        // returns src/main/resources/templates/login.html
        return "login";
    }
}

