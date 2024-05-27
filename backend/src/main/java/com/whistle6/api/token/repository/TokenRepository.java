package com.whistle6.api.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whistle6.api.token.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>, TokenQueryDSL{

    void deleteByUserId(Long id);    
}
