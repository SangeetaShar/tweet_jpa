package com.jpmc.service;

import com.jpmc.entity.Tweet;
import com.jpmc.entity.User;
import com.jpmc.model.TweetModel;
import com.jpmc.model.TweetRequestBody;
import com.jpmc.repo.TweetRepository;
import com.jpmc.utils.TestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserService userService;

    @Mock
    private Principal principalUser;

    @InjectMocks
    private TweetService tweetService;

    private String VALID_USERNAME = "somename";
    private String VALID_USERNAME_2 = "somename2";
    private String VALID_USERNAME_3 = "somename3";
    protected User user1, user2, user3;

    private List<Tweet> tweetListForUser1;
    private List<Tweet> tweetListForUser2;

    @Before
    public void setUp() {
        tweetRepository = Mockito.mock(TweetRepository.class);
        userService = Mockito.mock(UserService.class);
        principalUser = mock(Principal.class);
        setTestData();

        tweetService = new TweetService(tweetRepository, userService);
    }

    private void setTestData() {
        user1 = TestHelper.buildUserWithName(VALID_USERNAME);
        user2 = TestHelper.buildUserWithName(VALID_USERNAME_2);
        user3 = TestHelper.buildUserWithName(VALID_USERNAME_3);
        tweetListForUser1 = Arrays.asList(buildTweet(user2, "data1"));
        tweetListForUser2 = Arrays.asList(buildTweet(user1, "data1"),buildTweet(user1, "data2"));
    }

    @Test
    public void testGetAllTweets(){
        when(principalUser.getName()).thenReturn(VALID_USERNAME);
        when(userService.findByUsername(VALID_USERNAME)).thenReturn(user1);

        when(tweetRepository.findTweetByOtherUserOrderByModifyDate(user1)).thenReturn(tweetListForUser1);

        List<TweetModel> allTweets = tweetService.getAllTweets(principalUser);

        Assert.assertNotNull(allTweets);
        assertEquals(1, allTweets.size());
    }

    @Test
    public void testGetMyTweets(){
        when(principalUser.getName()).thenReturn(VALID_USERNAME);
        when(userService.findByUsername(VALID_USERNAME)).thenReturn(user1);
        when(tweetRepository.findTweetByUserOrderByModifyDate(user1)).thenReturn(tweetListForUser1);

        List<TweetModel> allTweets = tweetService.getMyTweets(principalUser);

        Assert.assertNotNull(allTweets);
        assertEquals(1, allTweets.size());
    }

    @Test
    public void testCreateTweets(){
        String data2_for_user1 = "data2 for user1";
        when(principalUser.getName()).thenReturn(VALID_USERNAME);
        when(userService.findByUsername(VALID_USERNAME)).thenReturn(user1);
        Tweet buildTweet = buildTweet(user1, data2_for_user1);
        when(tweetRepository.save(buildTweet)).thenReturn(buildTweet);

        TweetRequestBody tweetRequestBody = TweetRequestBody.builder().tweetData(data2_for_user1).build();
        tweetService.createTweet(tweetRequestBody, principalUser);

        verify(tweetRepository, times(1)).save(any(Tweet.class));
    }

    @Test
    public void testUpdateTweets(){
        String data2_for_user1 = "data2 for user1";
        Tweet buildTweet = buildTweet(user1, data2_for_user1);
        when(tweetRepository.findById(1l)).thenReturn(Optional.of(buildTweet));
        when(tweetRepository.save(buildTweet)).thenReturn(buildTweet);

        TweetRequestBody tweetRequestBody = TweetRequestBody.builder().tweetData(data2_for_user1).build();
        tweetService.updateTweet(1l, tweetRequestBody);

        verify(tweetRepository, times(1)).save(any(Tweet.class));
    }

    @Test
    public void testDeleteTweets(){
        tweetService.deleteTweet(1l);
        verify(tweetRepository, times(1)).deleteById(1l);
    }


    private Tweet buildTweet(User user, String data) {
        Tweet tweet = new Tweet();
        tweet.setTweetData(data);
        tweet.setUser(user);
        tweet.setModifyDate(new Date());
        return tweet;
    }

}
