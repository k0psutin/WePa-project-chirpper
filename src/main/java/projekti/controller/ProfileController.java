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
public class ProfileController {

    @Autowired
    private FollowService followService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;
    
    @GetMapping("/feed")
    public String userFeed(Model model) {
        Account current = accountService.getCurrentUser();
        List<Post> posts = postService.getUserFeed(current.getId());
        if (posts.isEmpty()) {
            posts = postService.getPosts(current);
        }
        if(posts.size() > 25) {
            posts = posts.subList(0, 25);
        }
        
        model.addAttribute("currentUsername", current.getUsername());
        model.addAttribute("posts", posts);
        model.addAttribute("user", current);
        return "feed";
    }

    @GetMapping("/profile/{username}")
    public String userProfile(Model model, @PathVariable String username) {
        Account user = accountService.getAccount(username);
        Account current = accountService.getCurrentUser();
        List<Post> posts = postService.getPosts(user);
      
        if(posts.size() > 25) {
            posts = posts.subList(0, 25);
        }
        
        Map<String, Boolean> checkList = followService.checkList(current, user);
        System.out.println(checkList);
        model.addAttribute("currentUsername", current.getUsername());
        model.addAttribute("current", checkList.get("isCurrentUser"));
        model.addAttribute("user", user);
        model.addAttribute("posts", posts);
        model.addAttribute("blocked", checkList.get("isBlocked"));
        model.addAttribute("follow", checkList.get("isFollowing"));
        return "profile";
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