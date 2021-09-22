package com.jpmc.repo;

import com.jpmc.entity.Tweet;
import com.jpmc.entity.User;
import com.jpmc.utils.TestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomTweetRepositoryImplTest {

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    UserRepository userRepository;

    protected User user1, user2, user3;

    private void setupTestData() {

        user1 = TestHelper.buildUser();
        user1 = userRepository.save(user1);

        user3 = TestHelper.buildUser();
        user3 = userRepository.save(user3);

        user2 = TestHelper.buildUser();
        user2 = userRepository.save(user2);

        tweetRepository.save(buildTweet(user1, "some tweet by user 1"));
        tweetRepository.save(buildTweet(user2, "Tweet 1 by user 2"));
        tweetRepository.save(buildTweet(user2, "Tweet 2 by user 2"));
        tweetRepository.save(buildTweet(user3, "Tweet 3 by user 3"));
    }

    @Before
    public void setUp() {
        setupTestData();
    }

    @Test
    public void testTweetByUserReturnsTweetByThatUserOnly()
    {
        List<Tweet> tweets = tweetRepository.findTweetByUserOrderByModifyDate(user1);
        Assert.assertNotNull(tweets);
        assertEquals(1, tweets.size());
        tweets = tweetRepository.findTweetByUserOrderByModifyDate(user2);

        Assert.assertNotNull(tweets);
        assertEquals(2, tweets.size());
    }

    @Test
    public void testTweetByUserReturnsTweetByOtherUserOnly()
    {
        List<Tweet> tweets = tweetRepository.findTweetByOtherUserOrderByModifyDate(user1);
        Assert.assertNotNull(tweets);
        assertEquals(3, tweets.size());
        tweets = tweetRepository.findTweetByOtherUserOrderByModifyDate(user3);

        Assert.assertNotNull(tweets);
        assertEquals(3, tweets.size());
    }

    private Tweet buildTweet(User user, String data) {
        Tweet tweet = new Tweet();
        tweet.setTweetData(data);
        tweet.setUser(user);
        return tweet;
    }
}
