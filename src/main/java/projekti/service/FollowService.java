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
    public Boolean isUserBlocked(Account current, Account toFollow) {
         Follow follow = followRepository.findByUserAndFollowing(current, toFollow);
         if(follow == null) {
             return false;
         }
         if(follow.getUser().getUsername().equals(follow.getFollowing().getUsername())) {
             return false;
         }
         return follow.getBlocked();
    }
    
    public Boolean doesUserFollow(Account current, Account toFollow) {
        Follow follow = followRepository.findByUserAndFollowing(current, toFollow);
        if (follow == null) {
            return false;
        } else {
            return true;
        }
    }

    public void followUser(String user) {
        Account current = accountService.getCurrentUser();
        Account toFollow = accountService.getAccount(user);

        // Jos käyttäjä seuraa jo henkilöä, ei tapahdu mitään..
        if (!doesUserFollow(current, toFollow)) {
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

        Follow stopFollowing = followRepository.findByUserAndFollowing(current, followerUser);
        Follow removeFollower = followRepository.findByUserAndFollowing(followerUser, current);
        if (stopFollowing != null) {
            System.out.println(stopFollowing.getUser().getUsername() + " wants to unfollow " + stopFollowing.getFollowing().getUsername());
            stopFollowing.setBlocked(true);
            followRepository.save(stopFollowing);
        } else {
            stopFollowing = new Follow();
            stopFollowing.setFollowing(followerUser);
            stopFollowing.setUser(current);
            stopFollowing.setBlocked(true);
            stopFollowing.setDateTime(LocalDateTime.now());
            followRepository.save(stopFollowing);
        }

        if (removeFollower != null) {
            System.out.println(removeFollower.getFollowing().getUsername() + " will be removed from " + removeFollower.getUser().getUsername() + " flock.");
            removeFollower.setBlocked(true);
            followRepository.save(removeFollower);
        } else {
            removeFollower = new Follow();
            removeFollower.setUser(followerUser);
            removeFollower.setFollowing(current);
            removeFollower.setBlocked(true);
            removeFollower.setDateTime(LocalDateTime.now());
            followRepository.save(removeFollower);
        }
    }
}
