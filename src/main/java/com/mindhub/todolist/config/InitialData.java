package com.mindhub.todolist.config;

import com.mindhub.todolist.entity.UserEntity;
import com.mindhub.todolist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@Configuration
public class InitialData {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitialData(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository) {
        return args -> {
            UserEntity adminUser = new UserEntity();

            adminUser.setUsername("David");
            adminUser.setEmail("david@email.com");
            adminUser.setPassword(passwordEncoder.encode("123456"));
            adminUser.setAuthorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));

            userRepository.save(adminUser);
        };

    }
}
