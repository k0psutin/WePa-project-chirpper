package projekti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projekti.model.*;

import java.util.*;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAccount(Account account);

    List<Post> findAllByAccountOrderByDateDescTimeDesc(Account account);

    @Query(value = "SELECT TOP 25 * FROM POST WHERE account_id = :id OR (SELECT FOLLOW_USER_ID FROM FOLLOW WHERE USER_ID = :id) ORDER BY DATE,TIME DESC", nativeQuery = true)
    List<Post> getUserFeed(@Param("id") long id);
}
