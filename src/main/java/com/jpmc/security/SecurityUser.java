package com.jpmc.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;

public class SecurityUser extends User {

    public SecurityUser(com.jpmc.entity.User user) {
        super(user.getUsername(),
              user.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
    }

}
