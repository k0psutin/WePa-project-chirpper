package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projekti.service.*;

@Controller
public class FollowController {

    @Autowired
    private FollowService followService;

    @GetMapping("/profile/{username}/follow")
    public String followUser(RedirectAttributes redirectAttributes, @PathVariable String username) {
        followService.requestFollow(username);
        redirectAttributes.addAttribute("user", username);
        return "redirect:/profile/{user}";
    }
}