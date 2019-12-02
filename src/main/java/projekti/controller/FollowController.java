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

    @GetMapping("/profile/{username}")
    public String userFeed(Model model, @PathVariable String username) {
        Account user = accountService.getAccount(username);
        Account current = accountService.getCurrentUser();
        List<Post> posts = postService.getUserFeed(user.getId());
        if (posts.isEmpty()) {
            posts = postService.getPosts(user);
        }
        
        Boolean isFollowingUser = followService.doesUserFollow(current, user);
        Boolean isCurrentUser = current.getUsername().equals(user.getUsername());
        Boolean isBlocked = followService.isUserBlocked(current, user);
        
        model.addAttribute("current", isCurrentUser);
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("blocked", isBlocked);
        model.addAttribute("follow", isFollowingUser);
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

    @GetMapping("/profile/follow/block/{follow}")
    public String blockUser(@PathVariable String follow) {
        followService.blockFollower(follow);
        return "redirect:/profile/follows";
    }
}