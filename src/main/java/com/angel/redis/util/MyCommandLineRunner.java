package com.angel.redis.util;

import com.angel.redis.model.User;
import com.angel.redis.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {

    private final UserRepository userRepository;

    @Autowired
    public MyCommandLineRunner(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        TO DO: Create and save users to the Repo
        User user = new User("John", 15000);
        User user1 = new User("Mon", 10000);
        User user2 = new User("Hon", 25000);

        userRepository.saveAll(Arrays.asList(user, user1, user2));
        log.error("Done saving users");
    }


}
