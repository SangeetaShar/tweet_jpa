package com.jpmc.utils;

import com.jpmc.entity.User;

import java.util.Random;
import java.util.UUID;

import static java.lang.String.format;

public class TestHelper {
    public static User buildUser() {
        String uuid = UUID.randomUUID().toString();
        return User.builder()
                .username("username-"+uuid)
                .password(uuid)
                .name("name-"+uuid)
                .role("ROLE_USER")
                .build();
    }

    public static User buildUserWithId() {
        Random random = new Random();
        String uuid = UUID.randomUUID().toString();
        return User.builder()
                .id(random.nextLong())
                .username("username-"+uuid)
                .password(uuid)
                .name("name-"+uuid)
                .role("ROLE_USER")
                .build();
    }

    public static User buildUserWithName(String userName) {
        Random random = new Random();
        String uuid = UUID.randomUUID().toString();
        return User.builder()
                .id(random.nextLong())
                .username("username-"+userName)
                .password(uuid)
                .name("name-"+uuid)
                .role("ROLE_USER")
                .build();
    }


}
