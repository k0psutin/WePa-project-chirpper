package projekti.service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.model.Post;
import projekti.repository.PostRepository;
import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import projekti.model.Account;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountService accountService;

    public void createPost(String username, String content) {
        if (username.length() == 0 | content.length() == 0) {
            return;
        }
        System.out.println(LocalDateTime.now());
        Account acc = accountService.getAccount(username);
        Post post = new Post();
        post.setAccount(acc);
        post.setContent(content);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        post.setTime(LocalTime.now().format(formatter));
        post.setDate(LocalDate.now().format(formatter2));
        postRepository.save(post);
    }

    public List<Post> getUserFeed(long id) {
        return postRepository.getUserFeed(id);
    }

    public Post getPostById(long id) {
        return postRepository.getOne(id);
    }

    public void likeAPost(Long id) {
        Post post = postRepository.getOne(id);
        System.out.println("Post id: " + id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account acc = accountService.getAccount(username);
        if (!post.getLikes().contains(acc)) {
            post.getLikes().add(acc);
            postRepository.save(post);
        }
    }

    public List<Post> getPosts(Account account) {
        return postRepository.findAllByAccountOrderByDateDescTimeDesc(account);
    }
}
