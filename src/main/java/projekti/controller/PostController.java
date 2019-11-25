package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import projekti.repository.AccountRepository;
import projekti.model.*;
import projekti.service.*;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/profile/{username}/post")
    public String newPost(Model model, @PathVariable String username, @RequestParam String content) {
        postService.createPost(username, content);
        model.addAttribute("user", accountService.getAccount(username));
        return "redirect:/profile/{username}";
    }

    @GetMapping("/profile/{username}/post/{id}/like")
    public String likePost(Model model, @PathVariable String username, @PathVariable Long id) {
        postService.likeAPost(id);
        model.addAttribute("user", accountService.getAccount(username));
        return "redirect:/profile/{username}";
    }

    @PostMapping("/profile/{username}/post/{id}/comment")
    public String addComment(Model model, @PathVariable String username, @PathVariable Long id,
            @RequestParam String comment) {
        Account user = accountService.getCurrentUser();
        commentService.createComment(id, comment, user);
        model.addAttribute("user", accountService.getAccount(username));
        return "redirect:/profile/{username}";
    }
}
