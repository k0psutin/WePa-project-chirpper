package projekti.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.model.Comment;
import projekti.model.Post;
import projekti.repository.CommentRepository;
import projekti.repository.PostRepository;

@Service
public class CommentService {

        @Autowired
        private CommentRepository commentRepository;

        @Autowired
        private PostRepository postRepository;

        public void createComment(Long id, String comment, String user) {
                if (comment.length() == 0) {
                        return;
                }
                Comment cmt = new Comment();
                Post post = postRepository.getOne(id);

                cmt.setContent(comment);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                cmt.setTime(LocalTime.now().format(formatter));
                cmt.setDate(LocalDate.now().format(formatter2));
                cmt.setUsername(user);
                cmt.setPost(post);
                post.getComment().add(cmt);

                commentRepository.save(cmt);
                postRepository.save(post);
        }
}
