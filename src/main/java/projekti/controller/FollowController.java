package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projekti.service.*;

@Controller
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/profile/feed")
    public String userFeed(Model model) {
        model.addAttribute("follows", followService.getFollowByOrder());
        model.addAttribute("user", accountService.getCurrentUser());
        return "feed";
    }

    @GetMapping("/profile/follows")
    public String followList(Model model) {
        model.addAttribute("follows", followService.getFollows());
        model.addAttribute("user", accountService.getCurrentUser());
        return "follows";
    }

    @GetMapping("/profile/{username}/follow")
    public String followUser(RedirectAttributes redirectAttributes, @PathVariable String username) {
        followService.followUser(username);
        redirectAttributes.addAttribute("user", username);
        return "redirect:/profile/{user}";
    }

    @PostMapping("/profile/follow/{follower}")
    public String blockUser(RedirectAttributes redirectAttributes, @PathVariable String username,
            @PathVariable String follower) {
        followService.blockFollower(follower);
        redirectAttributes.addAttribute("user", username);
        return "redirect:/profile/{user}";
    }
}