package com.jpmc.repo;

import com.jpmc.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long>, CustomTweetRepository {

}
