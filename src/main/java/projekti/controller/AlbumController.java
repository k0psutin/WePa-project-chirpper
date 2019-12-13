package projekti.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projekti.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import projekti.model.Account;
import projekti.model.Photo;
import projekti.model.Post;

@Controller
public class AlbumController {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PhotoCommentService photoCommentService;
    
    @Autowired
    private FollowService followService;

    @GetMapping("/profile/{username}/album")
    public String photoAlbum(Model model, @PathVariable String username) {
        Account current = accountService.getCurrentUser();
        Account user = accountService.getAccount(username);
        
        Map<String, Boolean> checkList = followService.checkList(current, user);
        model.addAttribute("currentUsername", current.getUsername());
        model.addAttribute("blocked", checkList.get("isBlocked"));
        model.addAttribute("follow", checkList.get("isFollowingUser"));
        model.addAttribute("current", checkList.get("isCurrentUser"));
        model.addAttribute("photos", photoService.getPhotos(username));
        model.addAttribute("user", user);
        return "album";
    }
    
    @PostMapping("/profile/img/delete/{id}/")
    public String removePhoto(RedirectAttributes redirectAttributes, @PathVariable long id) {
        Photo photo = photoService.getPhoto(id);
        Account acc = accountService.getCurrentUser();
        if(photo.getAccount().equals(acc)) {
            if(id == acc.getProfilePicId()) {
                System.out.println("Poistetaan kuva joka on profiilikuvana");
                accountService.changeAvatar(acc.getUsername(), null);
            }
            photoService.deletePhoto(photo);
        }
        redirectAttributes.addAttribute("username", acc.getUsername());
        return "redirect:/profile/{username}/album";
    }

    @GetMapping("/profile/img/{id}/like/{username}")
    public String likePost(Model model, RedirectAttributes redirectAttributes, @PathVariable String username,
            @PathVariable Long id) {
        photoService.likeAImg(id);
        redirectAttributes.addAttribute("user", username);
        return "redirect:/profile/{username}/album";
    }

    @GetMapping(path = "/profile/img/{id}", produces = "image/*")
    @ResponseBody
    public byte[] get(@PathVariable Long id) {
        return photoService.getPhoto(id).getContent();
    }

    @PostMapping("/profile/{username}/img/{id}/comment")
    public String addComment(Model model, @PathVariable String username, @PathVariable Long id,
            @RequestParam String comment) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();
        photoCommentService.createComment(id, comment, user);
        model.addAttribute("user", accountService.getAccount(username));
        return "redirect:/profile/{username}/album";
    }

    @GetMapping("/profile/{username}/profile_pic/{id}")
    public String changeAvatar(Model model, @PathVariable Long id, @PathVariable String username) {
        model.addAttribute("user", accountService.changeAvatar(username, id));
        return "redirect:/profile/{username}/album";
    }

    @PostMapping("/profile/{username}/album")
    public String uploadPhoto(Model model, @RequestParam String story, @PathVariable String username,
            @RequestParam("file") MultipartFile file) throws IOException {
        photoService.uploadPhoto(username, story, file);
        model.addAttribute("user", accountService.getAccount(username));
        return "redirect:/profile/{username}/album";
    }
}
