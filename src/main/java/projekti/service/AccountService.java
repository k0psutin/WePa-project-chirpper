package projekti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.model.*;
import projekti.repository.*;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccount(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        return getAccount(currentUser);
    }

    public void saveAccount(Account acc) {
        try {
            accountRepository.save(acc);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Account changeAvatar(String username, Long id) {
        Account acc = accountRepository.findByUsername(username);
        acc.setProfilePicId(id);
        accountRepository.save(acc);
        return acc;
    }

    public void createAccount(String username, String password, String firstName, String lastName) {
        try {
            BCryptPasswordEncoder pswEncoder = new BCryptPasswordEncoder();
            String psw = pswEncoder.encode(password);
            Account account = new Account();
            account.setUsername(username);
            account.setFirstname(firstName);
            account.setLastname(lastName);
            account.setPassword(psw);
            accountRepository.save(account);
            System.out.println("Pitäisi tallentaa");
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
    }
}
