package com.jpmc.repo;

import com.jpmc.entity.Tweet;
import com.jpmc.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomTweetRepository {

    List<Tweet> findTweetByUserOrderByModifyDate(User user);

    List<Tweet> findTweetByOtherUserOrderByModifyDate(User user);
}
