package projekti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projekti.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
