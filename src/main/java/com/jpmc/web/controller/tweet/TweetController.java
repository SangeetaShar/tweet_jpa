package com.jpmc.web.controller.tweet;


import com.jpmc.config.TimeProvider;
import com.jpmc.entity.Tweet;
import com.jpmc.model.TweetModel;
import com.jpmc.model.TweetRequestBody;
import com.jpmc.service.TweetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RestController
@RequestMapping("/api/tweet")
@Slf4j
public class TweetController {

    private final TweetService tweetService;

    @Autowired
    public TweetController(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    @GetMapping("")
    public List<TweetModel> getTweets(Principal user) {
        log.info("process=get-tweets");
        return tweetService.getAllTweets(user);
    }

    @GetMapping("/myTweets")
    public List<TweetModel> getMyTweets(Principal user) {
        log.info("process=get-tweets");
        return tweetService.getMyTweets(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TweetModel> getTweet(@PathVariable Long id) {
        log.info("process=get-tweetById, tweet_id={}", id);
        Optional<TweetModel> user = tweetService.getTweetById(id);
        return user.map(u -> ResponseEntity.ok(u))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @ResponseStatus(CREATED)
    public TweetModel createTweet(@Valid @RequestBody TweetRequestBody tweetRequestBody, Principal user) {
        log.info("process=create-tweetRequestBody, tweet_data={}", tweetRequestBody.getTweetData());
        Tweet tweet = tweetService.createTweet(tweetRequestBody, user);
        return new TweetModel(tweet) ;
    }

    @PutMapping("/{id}")
    public TweetModel updateTweet(@Valid @PathVariable Long id, @RequestBody TweetRequestBody tweetRequestBody, Principal user) {
        log.info("process=update-tweet, tweet_id={}", id);
        Tweet tweet = tweetService.updateTweet(id, tweetRequestBody);
        return new TweetModel(tweet) ;
    }

    @DeleteMapping("/{id}")
    public void deleteTweet(@PathVariable Long id) {
        log.info("process=delete-user, user_id={}", id);
        tweetService.deleteTweet(id);
    }

}
