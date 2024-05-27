package com.whistle6.api.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whistle6.api.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserQueryDSL{    
}
