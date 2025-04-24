package com.example.news_service.repository;

import com.example.news_service.entities.Football;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FootballRepository extends JpaRepository<Football, Long> {

}
