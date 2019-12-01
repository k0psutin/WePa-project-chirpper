package projekti.service;

import java.time.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.model.Post;
import projekti.repository.PostRepository;
import java.util.*;
import projekti.model.Account;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountService accountService;

    @Transactional
    public void createPost(String username, String content) {
        Account acc = accountService.getCurrentUser();

        if (!acc.getUsername().equals(username) | username.length() == 0 | content.length() == 0) {
            return;
        }
        Post post = new Post();
        post.setAccount(acc);
        post.setContent(content);
        post.setDateTime(LocalDateTime.now());
        postRepository.save(post);
    }

    public List<Post> getUserFeed(long id) {
        return postRepository.getUserFeed(id);
    }

    public Post getPostById(long id) {
        return postRepository.getOne(id);
    }

    @Transactional
    public void likeAPost(Long id) {
        Account acc = accountService.getCurrentUser();
        Post post = postRepository.getOne(id);
        System.out.println("Post id: " + id);

        if (!post.getLikes().contains(acc)) {
            post.getLikes().add(acc);
            postRepository.save(post);
        }
    }

    public List<Post> getPosts(Account account) {
        return postRepository.findAllByAccountOrderByDateTimeDesc(account);
    }
}
