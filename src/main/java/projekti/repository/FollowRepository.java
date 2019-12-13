package projekti.repository;

import projekti.model.*;
import org.springframework.data.jpa.repository.*;
import java.util.*;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Follow findByAccountAndFollow(Account account, Account following);

    List<Follow> findAllByAccount(Account account);

    List<Follow> findAllByAccountOrderByDateTime(Account account);

    List<Follow> findByFollow(Account following);
}
