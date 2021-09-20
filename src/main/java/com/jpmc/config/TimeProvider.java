package com.jpmc.config;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TimeProvider {

    public static Date now() {
        return new Date();
    }
}
