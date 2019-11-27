package projekti.service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projekti.model.*;
import projekti.repository.*;

@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private AccountService accountService;

    // Ohjaa tänne feedissä tykkäämiset, ettei palaa profiiliin..
    // Ja pitäisi varmaan tehdä JS:llä tykkäys?

    public void followUser(String user) {
        Account current = accountService.getCurrentUser();
        Account toFollow = accountService.getAccount(user);

        List<Follow> follows = followRepository.findByUserAndFollowing(current, toFollow);

        Boolean ifUserMatch = follows.stream()
                .anyMatch(follow -> follow.getUser().equals(current) && follow.getFollowing().equals(toFollow));
        // Boolean ifFollowerMatch = follows.stream()
        // .anyMatch(follow -> follow.getUser().equals(toFollow) &&
        // follow.getFollowing().equals(current));

        // System.out.println("ifUserMatch: " + ifUserMatch + " ifFollowerMatch: " +
        // ifFollowerMatch);

        // Jos käyttäjä seuraa jo henkilöä, ei tapahdu mitään..
        if (!ifUserMatch) {
            Follow follow = new Follow();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            follow.setTime(LocalTime.now().format(formatter));
            follow.setDate(LocalDate.now().format(formatter2));
            follow.setFollowing(toFollow);
            follow.setUser(current);
            follow.setBlocked(false);

            followRepository.save(follow);
        } else {
            // Tähän joku errori että voi näyttää sivuilla?
            System.out.println("Ei lisätä, löytyy jo");
        }

    }

    public List<Follow> getFollowingUser(Account account) {
        return followRepository.findByFollowing(account);
    }

    public List<Follow> getFollowsByUser(Account account) {
        return followRepository.findAllByUser(account);
    }

    public List<Follow> getFollowByOrder() {
        return followRepository.findAllByUserOrderByDate(accountService.getCurrentUser());
    }

    public void blockFollower(String follower) {
        Account current = accountService.getCurrentUser();
        Account followerUser = accountService.getAccount(follower);

        List<Follow> follows = followRepository.findByUserAndFollowing(current, followerUser);

        Follow followerMatch = follows.stream()
                .filter(follow -> follow.getUser().equals(followerUser) && follow.getFollowing().equals(current))
                .findFirst().get();

        if (!followerMatch.getBlocked()) {
            followerMatch.setBlocked(true);
            followRepository.save(followerMatch);
        }
    }
}
