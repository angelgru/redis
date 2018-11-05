package com.angel.redis.controller;

import com.angel.redis.model.User;
import com.angel.redis.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = "users", key = "#userId", unless = "#result.followers < 10000")
    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Long userId) throws Exception {
        log.error("Getting data for user " + userId);
        return userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
    }

    @CachePut(value = "users", key = "#userId")
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable("userId") Long userId, @RequestBody User userRequest) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User does not exist"));
        User saveUser = new User(userRequest.getName(), userRequest.getFollowers());
        saveUser.setId(user.getId());
        return userRepository.save(saveUser);
    }

    @CacheEvict(value = "users", key = "#userId")
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User Not found"));
        userRepository.delete(user);
    }
}
