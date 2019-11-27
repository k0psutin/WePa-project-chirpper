package projekti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projekti.model.*;

import java.util.*;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAccount(Account account);

    List<Post> findAllByAccountOrderByDateDescTimeDesc(Account account);

    @Query(value = "SELECT TOP 25 * FROM POST "
            + "WHERE ACCOUNT_ID IN (SELECT Follow_user_id FROM FOLLOW WHERE User_id = :id) "
            + "OR ACCOUNT_ID IN (SELECT user_id FROM FOLLOW WHERE User_id = :id) "
            + "ORDER BY DATE, TIME DESC", nativeQuery = true)
    List<Post> getUserFeed(@Param("id") long id);
}
