package projekti.repository;

import org.springframework.data.jpa.repository.*;
import projekti.model.Account;
import java.util.*;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @EntityGraph(attributePaths = {"account", "post", "comment"})
    @Override
    List<Account> findAll();
    
    Account findByUsername(String username);
}
