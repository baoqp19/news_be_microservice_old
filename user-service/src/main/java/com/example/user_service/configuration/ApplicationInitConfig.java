package com.example.user_service.configuration;

import com.example.user_service.entity.Timestamps;
import com.example.user_service.entity.User;
import com.example.user_service.enums.Role;
import com.example.user_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;

@Slf4j
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository repository) {
        return args -> {
            if (repository.findByUsername("quocbaodtr70").isEmpty()) {
                var role = new HashSet<String>();
                role.add(Role.ADMIN.name());

                User user = User.builder()
                        .name("quocbaodtr70")
                        .username("quocbaodtr70")
                        .email("quocbaodtr70@gmail.com")
                        .password(passwordEncoder.encode("12345678"))
                        .roles(role)
                        .timestamps(Timestamps.builder()
                                .created_at(LocalDateTime.now())
                                .updated_at(LocalDateTime.now())
                                .build())
                        .build();

                repository.save(user);

                log.warn("admin user has bean create with default password: admin, please change it");
            }
        };
    }

}
