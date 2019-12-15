package projekti.service;

import java.time.*;
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

    public Map<String, Boolean> checkList(Account current, Account toFollow) {
        Follow follow = followRepository.findByAccountAndFollow(current, toFollow);
        Map<String, Boolean> checkList = new HashMap<>();

        if (follow == null || follow.getAccount().getUsername().equals(follow.getFollow().getUsername())) {
            checkList.put("isFollowing", Boolean.FALSE);
            checkList.put("isBlocked", Boolean.FALSE);
        } else {
            checkList.put("isFollowing", Boolean.TRUE);
            checkList.put("isBlocked", follow.getBlocked());
        }
        
        checkList.put("isCurrentUser", current.getUsername().equals(toFollow.getUsername()));
        return checkList;
    }

    public Boolean doesUserFollow(Account current, Account toFollow) {
        Follow follow = followRepository.findByAccountAndFollow(current, toFollow);
        return !(follow == null);
    }

    public void followUser(String user) {
        Account current = accountService.getCurrentUser();
        Account toFollow = accountService.getAccount(user);

        if (!doesUserFollow(current, toFollow)) {
            Follow follow = new Follow();
            follow.setDateTime(LocalDateTime.now());
            follow.setFollow(toFollow);
            follow.setAccount(current);
            follow.setBlocked(false);

            followRepository.save(follow);
        } else {
            //System.out.println("Ei lisätä, löytyy jo");
        }

    }

    public List<Follow> getFollowingUser(Account account) {
        return followRepository.findByFollow(account);
    }

    public List<Follow> getFollowsByUser(Account account) {
        return followRepository.findAllByAccount(account);
    }

    public List<Follow> getFollowByOrder() {
        return followRepository.findAllByAccountOrderByDateTime(accountService.getCurrentUser());
    }

    public void blockFollower(String follower) {
        Account current = accountService.getCurrentUser();
        Account followerUser = accountService.getAccount(follower);

        Follow stopFollowing = followRepository.findByAccountAndFollow(current, followerUser);
        Follow removeFollower = followRepository.findByAccountAndFollow(followerUser, current);
        if (stopFollowing != null) {
            //System.out.println(stopFollowing.getAccount().getUsername() + " wants to unfollow " + stopFollowing.getFollow().getUsername());
            stopFollowing.setBlocked(true);
            followRepository.save(stopFollowing);
        } else {
            stopFollowing = new Follow();
            stopFollowing.setFollow(followerUser);
            stopFollowing.setAccount(current);
            stopFollowing.setBlocked(true);
            stopFollowing.setDateTime(LocalDateTime.now());
            followRepository.save(stopFollowing);
        }

        if (removeFollower != null) {
            //System.out.println(removeFollower.getAccount().getUsername() + " will be removed from " + removeFollower.getAccount().getUsername() + " flock.");
            removeFollower.setBlocked(true);
            followRepository.save(removeFollower);
        } else {
            removeFollower = new Follow();
            removeFollower.setAccount(followerUser);
            removeFollower.setFollow(current);
            removeFollower.setBlocked(true);
            removeFollower.setDateTime(LocalDateTime.now());
            followRepository.save(removeFollower);
        }
    }
}
