package projekti.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projekti.model.Account;
import projekti.model.Post;
import projekti.service.AccountService;
import projekti.service.CommentService;
import projekti.service.FollowService;
import projekti.service.PostService;

@Controller
public class PostCommentController {

    // Ei hajua miten vaihdetaan sivuja..
    @Autowired
    private AccountService accountService;

    @Autowired
    private FollowService followService;
    
    

}
