package com.ecommerce.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableDiscoveryClient
public class UserserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserserviceApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setName("System");
                admin.setSurname("Administrator");
                admin.setEmail("admin@ecommerce.com");
                admin.setPhoneNumber("0000000000");
                admin.setRole("ROLE_ADMIN");
                admin.setCreatedAt(LocalDateTime.now());
                userRepository.save(admin);
                System.out.println("Default Admin account created: admin / admin");
            }
        };
    }

}
