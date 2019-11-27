package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projekti.repository.*;
import projekti.model.*;
import projekti.service.*;

import java.util.*;

@Controller
public class PostCommentController {

    // Ei hajua miten vaihdetaan sivuja..

    @GetMapping("/profile/post/{id}/previous")
    public String changeCommentPage(Model model, @RequestParam String user, @PathVariable long id) {

        return "profile";
    }
}