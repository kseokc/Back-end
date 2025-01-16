package icurriculum.admin.web;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/login/admin")
public class AdminLoginController {

    @GetMapping
    public String loginForm(){
        return "login/admin-login";
    }

}
