package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projekti.model.*;
import projekti.service.*;

import java.util.*;

@Controller
public class FollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @GetMapping("/profile/feed")
    public String userFeed(Model model) {
        Account acc = accountService.getCurrentUser();
        List<Post> posts = postService.getUserFeed(acc.getId());
        if (posts.isEmpty()) {
            posts = postService.getPosts(acc);
        }
        model.addAttribute("user", acc);
        model.addAttribute("posts", posts);
        return "feed";
    }

    @GetMapping("/profile/follows")
    public String followList(Model model) {
        Account acc = accountService.getCurrentUser();
        model.addAttribute("following", followService.getFollowsByUser(acc));
        model.addAttribute("follows", followService.getFollowingUser(acc));
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