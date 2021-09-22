package com.jpmc.utils;

import com.jpmc.entity.User;
import com.jpmc.repo.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Arrays.asList;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {BaseIntegrationTest.Initializer.class})
public abstract class BaseIntegrationTest {

    @Autowired
    UserRepository userRepository;

    protected User existingUser, newUser, updateUser;

    protected void setupTestData() {
        newUser = TestHelper.buildUser();

        existingUser = TestHelper.buildUser();
        existingUser = userRepository.save(existingUser);

        updateUser = TestHelper.buildUser();
        updateUser = userRepository.save(updateUser);

        User mockUser = TestHelper.buildUserWithName("mockUser");
        userRepository.save(mockUser);
    }

    protected void cleanupTestData() {
        if(newUser.getId() != null) {
            userRepository.deleteById(newUser.getId());
        }
        userRepository.deleteAll(userRepository.findAllById(asList(existingUser.getId(), updateUser.getId())));
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=jdbc:h2:mem:testdb",
                    "spring.datasource.username=sa",
                    "spring.datasource.password=password",
                    "spring.datasource.driverClassName=org.h2.Driver",
                    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
