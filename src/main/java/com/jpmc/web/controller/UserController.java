package com.jpmc.web.controller;

import com.jpmc.entity.User;
import com.jpmc.model.UserModel;
import com.jpmc.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/users")
@Slf4j
@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public List<UserModel> getUsers() {
        log.info("process=get-users");
        List<UserModel> userModels = new ArrayList<>();
        for(User localUser : userService.getAllUsers()){
            userModels.add(new UserModel(localUser));
        }
        return userModels;
    }

    @GetMapping("/{username}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserModel> getUser(@PathVariable String username) {
        log.info("process=get-user, userName={}", username);
        User user = userService.findByUsername(username);
        return user!= null ? ResponseEntity.ok(new UserModel(user))
                   : ResponseEntity.notFound().build();
    }

    @PostMapping("")
    @ResponseStatus(CREATED)
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public UserModel createUser(@RequestBody User user) {
        log.info("process=create-user, user={}", user);
        return new UserModel(userService.createUser(user));
    }

    @PutMapping("/{username}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public UserModel updateUser(@PathVariable String username, @RequestBody User user) {
        log.info("process=upadte-user, userName={}", username);
        User existingUser = userService.findByUsername(username);
        if (user != null){
            Long id = existingUser.getId();
            log.info("process=update-user, user_id={}", id);
            user.setId(id);
            return new UserModel(userService.updateUser(user));
        } else {
            return new UserModel(existingUser);
        }
    }

    @DeleteMapping("/{username}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable String username) {
        log.info("process=delete-user, userName={}", username);
        User user = userService.findByUsername(username);
        if (user != null){
            Long id = user.getId();
            log.info("process=delete-user, user_id={}", id);
            userService.deleteUser(id);
        }

    }

}
