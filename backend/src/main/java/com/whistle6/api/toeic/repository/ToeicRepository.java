package com.whistle6.api.toeic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.whistle6.api.toeic.model.Toeic;

@Repository
public interface ToeicRepository extends JpaRepository<Toeic, Long>, ToeicQueryDSL{
}
