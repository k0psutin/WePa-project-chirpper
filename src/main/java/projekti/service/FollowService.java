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

    public Boolean doesUserFollow(Account current, Account toFollow) {
        return !followRepository.findByUserAndFollowing(current, toFollow).stream()
                .anyMatch(follow -> follow.getUser().equals(current) && follow.getFollowing().equals(toFollow));
    }

    public void followUser(String user) {
        Account current = accountService.getCurrentUser();
        Account toFollow = accountService.getAccount(user);

        // Boolean ifFollowerMatch = follows.stream()
        // .anyMatch(follow -> follow.getUser().equals(toFollow) &&
        // follow.getFollowing().equals(current));

        // System.out.println("ifUserMatch: " + ifUserMatch + " ifFollowerMatch: " +
        // ifFollowerMatch);

        // Jos käyttäjä seuraa jo henkilöä, ei tapahdu mitään..
        if (doesUserFollow(current, toFollow)) {
            Follow follow = new Follow();
            follow.setDateTime(LocalDateTime.now());
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
        return followRepository.findAllByUserOrderByDateTime(accountService.getCurrentUser());
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
