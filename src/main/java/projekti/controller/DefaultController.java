package projekti.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DefaultController {

    @GetMapping("/")
    public String afterLogin(RedirectAttributes redirectAttributes) {
        System.out.println("DefaultController");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        redirectAttributes.addAttribute("username", currentUser);
        return "redirect:/profile/{username}";
    }
}
