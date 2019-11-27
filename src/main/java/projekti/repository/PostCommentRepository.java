package projekti.repository;

import projekti.model.*;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {
    Page<PostComment> findAllByUser(Account user, Pageable pageable);

    Page<PostComment> findAllByPost(Post post, Pageable pageable);
}
