package com.jpmc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmc.entity.Tweet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetModel {

    @JsonProperty("tweetData")
    @Size(max = 160, message = "Tweet can not be longer then 160 characters")
    private String tweetData;

    @JsonProperty("tweetDate")
    private Timestamp tweetDate;

    public TweetModel(Tweet tweet) {
        this.tweetData = tweet.getTweetData();
        this.tweetDate = new Timestamp(tweet.getModifyDate().getTime());
    }
}
