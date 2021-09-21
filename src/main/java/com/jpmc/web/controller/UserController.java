package com.jpmc.web.controller;

import com.jpmc.entity.User;
import com.jpmc.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
    public List<User> getUsers() {
        log.info("process=get-users");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        log.info("process=get-user, user_id={}", id);
        Optional<User> user = userService.getUserById(id);
        return user.map( u -> ResponseEntity.ok(u))
                   .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/myData")
    public ResponseEntity<User> getCurrentUser(Principal principalUser) {
        log.info("process=get-user, name={}", principalUser.getName());
        User user = userService.findByUsername(principalUser.getName());
        return (user != null ?ResponseEntity.ok(user)
                : ResponseEntity.notFound().build());
    }
    @PostMapping("")
    @ResponseStatus(CREATED)
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public User createUser(@RequestBody User user) {
        log.info("process=create-user, user={}", user);
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        log.info("process=update-user, user_id={}", id);
        user.setId(id);
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable Long id) {
        log.info("process=delete-user, user_id={}", id);
        userService.deleteUser(id);
    }

}
