package com.jpmc.repo;

import com.jpmc.entity.Tweet;
import com.jpmc.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class CustomTweetRepositoryImpl implements CustomTweetRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Tweet> findTweetByUserOrderByModifyDate(User user) {


        Query query = entityManager.createNativeQuery("SELECT tw.* FROM public.tweet as tw " +
                "WHERE tw.userid = ? order by tw.modify_date asc", Tweet.class);
        query.setParameter(1, user.getId());

        return query.getResultList();
    }

    @Override
    public List<Tweet> findTweetByOtherUserOrderByModifyDate(User user) {


        Query query = entityManager.createNativeQuery("SELECT tw.* FROM public.tweet as tw " +
                "WHERE tw.userid != ? order by tw.modify_date asc", Tweet.class);
        query.setParameter(1, user.getId());

        return query.getResultList();
    }
}
