package projekti.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import projekti.model.*;
import projekti.service.*;

@Controller
public class LoginController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/login/create")
    public String accountCreation(@ModelAttribute Account account) {
        return "create";
    }

    @GetMapping("login/create-success") 
    public String createSuccess(Model model) {
        model.addAttribute("createSuccess", true);
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/logout-success")
    public String logoutSuccess(Model model) {
        model.addAttribute("logoutSuccess", true);
        return "login";
    }

    @PostMapping("/login/create")
    public String createNewAccount(@Valid @ModelAttribute Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        System.out.println("Tekee accon");
        accountService.createAccount(account);
        return "redirect:/login";
    }
}
