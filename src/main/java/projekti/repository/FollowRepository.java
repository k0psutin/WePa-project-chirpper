package projekti.repository;

import projekti.model.*;
import org.springframework.data.jpa.repository.*;
import java.util.*;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    List<Follow> findByUserAndFollowing(Account user, Account following);

    List<Follow> findAllByUser(Account user);

    List<Follow> findAllByUserOrderByDate(Account user);

}
