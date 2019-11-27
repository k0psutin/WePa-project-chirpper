package projekti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostCommentController {

    // Ei hajua miten vaihdetaan sivuja..

    @GetMapping("/profile/post/{id}/previous")
    public String changeCommentPage(Model model, @RequestParam String user, @PathVariable long id) {

        return "profile";
    }
}