package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projekti.service.*;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/profile/")
    public String userpage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("current", auth.getName());
        model.addAttribute("user", accountService.getAccount(auth.getName()));
        return "profile";
    }

    @GetMapping("/profile/{user}")
    private String userPage(Model model, @PathVariable String user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("current", auth.getName());
        model.addAttribute("user", accountService.getAccount(user));
        return "profile";
    }

    @GetMapping("/test/{user}")
    private String test(Model model, @PathVariable String user) {
        model.addAttribute("user", accountService.getAccount(user));
        return "test";
    }

    @PostMapping("/profile/search")
    private String findUser(RedirectAttributes redirectAttributes, @RequestParam String user) {
        redirectAttributes.addAttribute("user", user);
        return "redirect:/profile/{user}";
    }
}
