package projekti.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.model.Account;
import projekti.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account getAccount(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account changeAvatar(String username, Long id) {
        Account acc = accountRepository.findByUsername(username);
        acc.setProfilePicId(id);
        accountRepository.save(acc);
        return acc;
    }

    public void createAccount(String username, String password, String firstName, String lastName) {
        BCryptPasswordEncoder pswEncoder = new BCryptPasswordEncoder();
        String psw = pswEncoder.encode(password);
        Account account = new Account();
        account.setUsername(username);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setPassword(psw);
        accountRepository.save(account);
        System.out.println("Pitäisi tallentaa");
    }
}
