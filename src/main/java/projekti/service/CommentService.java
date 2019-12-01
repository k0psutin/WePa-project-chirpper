package projekti.service;

import java.time.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.model.*;
import projekti.repository.*;

@Service
public class CommentService {

        @Autowired
        private PostCommentRepository postCommentRepository;

        @Autowired
        private PostRepository postRepository;

        @Transactional
        public void createComment(Long id, String comment, Account user) {
                if (comment.length() == 0) {
                        return;
                }
                PostComment cmt = new PostComment();
                Post post = postRepository.getOne(id);

                cmt.setContent(comment);
                cmt.setDateTime(LocalDateTime.now());
                cmt.setUser(user);
                cmt.setPost(post);
                postCommentRepository.save(cmt);
        }
}
