package projekti.repository;

import projekti.model.PhotoComment;
import org.springframework.data.jpa.repository.*;

public interface PhotoCommentRepository extends JpaRepository<PhotoComment, Long> {
}
