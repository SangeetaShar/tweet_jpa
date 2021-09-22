package com.jpmc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmc.entity.Tweet;
import com.jpmc.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    @JsonProperty("username")
    @Size(max = 160, message = "Tweet can not be longer then 160 characters")
    private String username;

    @JsonProperty("name")
    private String name;

    public UserModel(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
    }
}
