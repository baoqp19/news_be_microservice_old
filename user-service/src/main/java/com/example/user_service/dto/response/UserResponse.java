package com.example.user_service.dto.response;

import com.example.user_service.entity.Timestamps;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String name;
    String username;
    String email;
    Set<String> roles;
    Timestamps timestamps;
}
