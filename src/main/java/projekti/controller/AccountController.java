package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projekti.model.Account;
import projekti.service.*;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @GetMapping("/profile/")
    public String userpage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account acc = accountService.getAccount(auth.getName());
        model.addAttribute("current", auth.getName());
        model.addAttribute("posts", postService.getPosts(acc));
        model.addAttribute("user", acc);
        return "profile";
    }

    @GetMapping("/profile/{user}")
    private String userPage(Model model, @PathVariable String user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account username = accountService.getAccount(user);
        model.addAttribute("current", auth.getName());
        model.addAttribute("posts", postService.getPosts(username));
        model.addAttribute("user", username);
        return "profile";
    }

    @GetMapping("/test/{user}")
    private String test(Model model, @PathVariable String user) {
        Account acc = accountService.getAccount(user);
        model.addAttribute("posts", postService.getPosts(acc));
        model.addAttribute("user", acc);
        return "test";
    }

    @PostMapping("/profile/search")
    private String findUser(RedirectAttributes redirectAttributes, @RequestParam String user) {
        redirectAttributes.addAttribute("user", user);
        return "redirect:/profile/{user}";
    }
}
