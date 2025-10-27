package com.cemus.oozeer.service;


import com.cemus.oozeer.exception.UserAlreadyExistsException;
import com.cemus.oozeer.model.User;
import com.cemus.oozeer.model.UserInfoDetails;
import com.cemus.oozeer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserAuthService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        User userInfos = user.get();
        return new UserInfoDetails(userInfos);
    }

    public String addUser(User newUser) {
        try {
            Optional<User> user = repository.findByEmail(newUser.getEmail());

            if (user.isPresent()) {
                throw new UserAlreadyExistsException("Email already assigned: " + newUser.getEmail());
            }

            newUser.setPassword(encoder.encode(newUser.getPassword()));
            repository.save(newUser);
            return "User added successfully!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}