package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import projekti.service.AccountService;

@Controller
public class LoginController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/login/create")
    public String accountCreation() {
        return "createAccount";
    }

    @PostMapping("/login/create")
    public String createNewAccount(@RequestParam String username, @RequestParam String firstName,
            @RequestParam String lastName, @RequestParam String password) {
        accountService.createAccount(username, password, firstName, lastName);
        System.out.println("Pääseekö");
        return "redirect:/login";
    }
}
