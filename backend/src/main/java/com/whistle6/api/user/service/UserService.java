package com.whistle6.api.user.service;

import com.whistle6.api.common.command.CommandService;
import com.whistle6.api.common.component.Messenger;
import com.whistle6.api.common.query.QueryService;
import com.whistle6.api.user.model.User;
import com.whistle6.api.user.model.UserDTO;

public interface UserService extends CommandService<User, UserDTO>, QueryService<User, UserDTO>{
    
    default UserDTO toDTO(User user) {
        return UserDTO.builder()
            .id(user.getId())
            .email(user.getEmail())
            .password(user.getPassword())
            .name(user.getName())
            .phone(user.getPhone())
            .role(user.getRole())
            .birth(user.getBirth())
            .build();
    }
    
    Messenger existsByEmail(String email);

    Messenger login(UserDTO userDTO);
}
