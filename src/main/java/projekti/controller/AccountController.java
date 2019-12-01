package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projekti.model.*;
import projekti.service.*;

import java.util.*;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private FollowService followService;

    @Autowired
    private PostService postService;

    @GetMapping("/profile/")
    public String userpage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account acc = accountService.getAccount(auth.getName());
        List<Post> post = postService.getPosts(acc);
        model.addAttribute("current", auth.getName());
        model.addAttribute("posts", post);
        model.addAttribute("user", acc);
        return "profile";
    }

    @GetMapping("/profile/{user}")
    private String userPage(Model model, @PathVariable String user) {
        Account current = accountService.getCurrentUser();
        Account acc = accountService.getAccount(user);
        List<Post> post = postService.getPosts(acc);
        model.addAttribute("current", current.getUsername());
        model.addAttribute("posts", post);
        model.addAttribute("user", acc);
        model.addAttribute("blocked", followService.isUserBlocked(current, acc));
        model.addAttribute("following", followService.doesUserFollow(current, acc));
        return "profile";
    }

    @PostMapping("/profile/search")
    private String findUser(RedirectAttributes redirectAttributes, @RequestParam String user) {
        // Tähän joku virheilmoitus että ei löydy käyttäjää x
        if (accountService.userExists(user)) {
            return "redirect:/profile/";
        }

        redirectAttributes.addAttribute("user", user);
        return "redirect:/profile/{user}";
    }
}
