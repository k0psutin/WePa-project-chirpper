package projekti.service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projekti.model.*;
import projekti.repository.*;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private AccountService accountService;

    public void requestFollow(String user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        Account current = accountService.getAccount(currentUser);
        Account toFollow = accountService.getAccount(user);

        List<Follow> follows = followRepository.findByUserAndFollowing(current, toFollow);

        // Tarkistus taitaa toimia nyt niin, että ei voi lisätä kuin kerran jos
        // tykkäys löytyy listalta...

        Boolean ifUserMatch = follows.stream()
                .anyMatch(follow -> follow.getUser().equals(current) && follow.getFollowing().equals(toFollow));
        Boolean ifFollowerMatch = follows.stream()
                .anyMatch(follow -> follow.getUser().equals(toFollow) && follow.getFollowing().equals(current));

        System.out.println("ifUserMatch: " + ifUserMatch + " ifFollowerMatch: " + ifFollowerMatch);

        // Jos ifUserMatch ja ifFollowerMatch molemmat on false, niin lisätään uusi..
        if (!ifUserMatch && !ifFollowerMatch) {
            Follow follow = new Follow();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            follow.setTime(LocalTime.now().format(formatter));
            follow.setDate(LocalDate.now().format(formatter2));
            follow.setFollowing(toFollow);
            follow.setUser(current);

            followRepository.save(follow);
        } else
            System.out.println("Ei lisätä, löytyy jo");
    }
}
