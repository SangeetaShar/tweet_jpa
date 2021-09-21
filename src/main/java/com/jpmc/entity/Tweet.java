package com.jpmc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TWEET")
public class Tweet {

    @Id
    @SequenceGenerator(name = "tweet_id_generator", sequenceName = "tweet_id_seq", initialValue = 10, allocationSize = 1)
    @GeneratedValue(generator = "tweet_id_generator", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "tweet_data")
    @Size(max = 160, message = "Tweet can not be longer then 160 characters")
    private String tweetData;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "ID")
    private User user;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    @Override
    public String toString() {
        return "Tweet{" +
                "tweetData='" + getTweetData() + '\'' +
                ", user=" + getUser() +
                '}';
    }
}
