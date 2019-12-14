package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projekti.model.*;

import projekti.service.*;

import java.util.*;
import org.springframework.ui.Model;

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
        List<Account> accs = accountService.userExists(user);
        
        if (accs.isEmpty()) {
            return "redirect:/";
        }

        redirectAttributes.addAttribute("user", accs.get(0).getUsername());
        return "redirect:/profile/{user}";
    }
    
   @GetMapping("/users")
   public String allUsers(Model model) {
       List<Account> users = accountService.getAllUsers();
       model.addAttribute("users", users);
       return "users";
   }
}
