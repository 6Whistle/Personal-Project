package com.whistle6.api.user.repository;

import com.whistle6.api.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserQueryDSL {
    Boolean existsByIdDSL(Long id);
    Boolean existsByEmailDSL(String email);    

    Long countDSL();

    List<User> findAllDSL();
    Optional<User> findByIdDSL(Long id);
    Optional<User> findByEmailDSL(String email);
}
