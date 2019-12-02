package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projekti.service.*;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private FollowService followService;

    @Autowired
    private PostService postService;

    @PostMapping("/profile/search")
    public String findUser(RedirectAttributes redirectAttributes, @RequestParam String user) {
        // Tähän joku virheilmoitus että ei löydy käyttäjää x
        if (accountService.userExists(user)) {
            return "redirect:/";
        }

        redirectAttributes.addAttribute("user", user);
        return "redirect:/profile/{user}";
    }
}
