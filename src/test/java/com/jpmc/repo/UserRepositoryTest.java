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
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

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
    }

    @Before
    public void setUp() {
        setupTestData();
    }

    @Test
    public void testFindByUserNameRetunsUserResult()
    {
        User user = userRepository.findByUsername(user1.getUsername());
        assertEquals(user1, user);
    }

    @Test
    public void testFindByUserNameRetunsNull()
    {
        User user = userRepository.findByUsername("invalid username");
        assertNull(user);
    }

}
