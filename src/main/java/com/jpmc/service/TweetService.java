package com.jpmc.service;

import com.jpmc.entity.Tweet;
import com.jpmc.entity.User;
import com.jpmc.model.TweetModel;
import com.jpmc.model.TweetRequestBody;
import com.jpmc.repo.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TweetService {
    private final TweetRepository tweetRepository;
    private final UserService userService;
    @Autowired
    public TweetService(TweetRepository tweetRepository, UserService userService) {
        this.tweetRepository = tweetRepository;
        this.userService = userService;
    }

    public List<TweetModel> getAllTweets(Principal user) {
        User byUsername = this.userService.findByUsername(user.getName());
        List<Tweet> tweetRepositoryAll = tweetRepository.findTweetByOtherUserOrderByModifyDate(byUsername);
        List<TweetModel> tweetModels = new ArrayList<>();
        for(Tweet tweet : tweetRepositoryAll){
            tweetModels.add(new TweetModel(tweet));
        }
        return tweetModels;
    }

    public Tweet createTweet(TweetRequestBody tweetRequestBody, Principal user) {
        User byUsername = this.userService.findByUsername(user.getName());
        Tweet tweet = new Tweet();
        tweet.setTweetData(tweetRequestBody.getTweetData());
        tweet.setUser(byUsername);
        return tweetRepository.save(tweet);
    }

    public Tweet updateTweet(Long id, TweetRequestBody tweetRequestBody) {
        Optional<Tweet> tweetById = tweetRepository.findById(id);
        Tweet tweet = tweetById.get();
        tweet.setTweetData(tweetRequestBody.getTweetData());
        return tweetRepository.save(tweet);
    }

    public void deleteTweet(Long tweetId) {
        tweetRepository.deleteById(tweetId);
    }

    public List<TweetModel> getMyTweets(Principal user) {
        User byUsername = this.userService.findByUsername(user.getName());
        List<Tweet> tweetRepositoryAll = tweetRepository.findTweetByUserOrderByModifyDate(byUsername);
        List<TweetModel> tweetModels = new ArrayList<>();
        for(Tweet tweet : tweetRepositoryAll){
            tweetModels.add(new TweetModel(tweet));
        }
        return tweetModels;
    }

}
