package com.jpmc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmc.entity.Tweet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetModel {

    @JsonProperty("tweetData")
    private String tweetData;

    @JsonProperty("tweetDate")
    private Timestamp tweetDate;

    public TweetModel(Tweet tweet) {
        this.tweetData = tweet.getTweetData();
        this.tweetDate = new Timestamp(tweet.getModifyDate().getTime());
    }
}
