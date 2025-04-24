package com.example.user_service.mapper;

import com.example.user_service.dto.request.UserCreationRequest;
import com.example.user_service.dto.request.UserUpdateRequest;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserCreationRequest userCreationRequest);
    UserResponse toUserResponse(User user);
    
    void updateUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

}
