package com.jpmc.web.controller;

import com.jpmc.entity.User;
import com.jpmc.model.AuthenticationRequest;
import com.jpmc.model.ChangePassword;
import com.jpmc.model.UserTokenState;
import com.jpmc.security.SecurityUser;
import com.jpmc.security.TokenHelper;
import com.jpmc.service.CustomUserDetailsService;
import com.jpmc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping( value = "/api")
public class AuthenticationController {

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/auth/login")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        SecurityUser user = (SecurityUser) authentication.getPrincipal();
        String jws = tokenHelper.generateToken( user.getUsername());
        long expiresIn = tokenHelper.getExpiredIn();
        return ResponseEntity.ok(new UserTokenState(jws, expiresIn));
    }


    @GetMapping(value="/auth/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){

            String authToken = tokenHelper.getToken( request );

            if (authToken != null ) {
                long expiresIn = 0l;
                tokenHelper.expireToken(authToken);
                auth.setAuthenticated(false);
                new SecurityContextLogoutHandler().logout(request, response, auth);
                return ResponseEntity.ok(new UserTokenState(authToken, expiresIn));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public User user(Principal user) {
        return this.userService.findByUsername(user.getName());
    }
}