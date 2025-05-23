package com.example.news_service.repository;

import com.example.news_service.entities.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT n FROM News n WHERE n.category = ?1")
    List<News> findByCategory(String category);

    List<News> findByTitleContainingIgnoreCase(String keyword);

//    @Query("SELECT n FROM News n WHERE n.category = ?1")
    Page<News> findByCategory(String category, Pageable pageable);
}
