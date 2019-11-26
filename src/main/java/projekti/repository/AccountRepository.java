package projekti.repository;

import org.springframework.data.jpa.repository.*;
import projekti.model.Account;
import java.util.*;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAll();

    Account findByUsername(String username);
}
