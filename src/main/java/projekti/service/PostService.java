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
    public Boolean createPost(String username, String content) {
        Account acc = accountService.getCurrentUser();

        if (!acc.getUsername().equals(username) | username.length() == 0 | content.length() < 3 | content.length() > 120) {
            return false;
        }
        Post post = new Post();
        post.setAccount(acc);
        post.setContent(content);
        post.setDateTime(LocalDateTime.now());
        postRepository.save(post);
        return true;
    }

    public List<Post> getUserFeed(long id) {
        return postRepository.getUserFeed(id);
    }

    public Post getPostById(long id) {
        return postRepository.getOne(id);
    }
    
    public void deletePost(Post post) {
        postRepository.delete(post);
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
