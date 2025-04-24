package com.example.notification_service.repository;

import com.example.notification_service.entity.PostNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface PostNewsRepository extends JpaRepository<PostNews, Long> {
    @Query("SELECT p FROM PostNews p WHERE p.userId = ?1")
    List<PostNews> findByUserId(Long userId);
}
