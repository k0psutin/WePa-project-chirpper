package projekti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.model.*;
import projekti.repository.*;

import java.util.*;

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

    @Transactional
    public void saveAccount(Account acc) {
        try {
            System.out.println("tallennetaan");
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

    public List<Account> userExists(String search) {
        return accountRepository.findAllByUsernameContaining(search);
    }

    @Transactional
    public void createAccount(Account account) {
        try {
            BCryptPasswordEncoder pswEncoder = new BCryptPasswordEncoder();
            String psw = pswEncoder.encode(account.getPassword());
            account.setPassword(psw);
            accountRepository.save(account);
            account.setProfilePicId(null);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
    }
}
