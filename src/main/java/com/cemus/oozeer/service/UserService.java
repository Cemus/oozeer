package com.cemus.oozeer.service;


import com.cemus.oozeer.model.User;
import com.cemus.oozeer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                            .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }
}