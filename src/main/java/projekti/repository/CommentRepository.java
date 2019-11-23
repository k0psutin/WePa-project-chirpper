package projekti.repository;

import projekti.model.Comment;
import org.springframework.data.jpa.repository.*;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
