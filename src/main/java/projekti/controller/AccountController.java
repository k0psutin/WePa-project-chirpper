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

   /*
    @GetMapping("/profile/")
    public String userpage(Model model) {
        System.out.println("get userpage");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account acc = accountService.getCurrentUser();
        List<Post> post = postService.getPosts(acc);
        model.addAttribute("current", auth.getName());
        model.addAttribute("posts", post);
        model.addAttribute("user", acc);
        return "profile";
    }
    */
    //Miksi hakee 2x tästä?
    /*
    @GetMapping("/profile/{user}")
    public String userProfile(Model model, @PathVariable String user) {
        System.out.println("get profile page");
        Account current = accountService.getCurrentUser();
        Account acc = accountService.getAccount(user);
        List<Post> post = postService.getPosts(acc);
        Boolean blocked = followService.isUserBlocked(current, acc);
        System.out.println("Blocked: " + blocked);
        model.addAttribute("current", current.getUsername().equals(acc.getUsername()));
        model.addAttribute("posts", post);
        model.addAttribute("user", acc);
        model.addAttribute("blocked", blocked);
        model.addAttribute("following", followService.doesUserFollow(current, acc));
        return "profile";
    }
    */

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
