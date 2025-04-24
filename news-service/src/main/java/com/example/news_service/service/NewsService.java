package com.example.news_service.service;

import com.example.news_service.dto.response.NewsDto;
import com.example.news_service.entities.News;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NewsService {
    List<NewsDto> findAll();
    Page<NewsDto> findAll(int page, int size, String sortBy, String sortOrder);
    List<NewsDto> findByCategory(String category);
    Page<NewsDto> findByCategory(String category, int page, int size, String sortBy, String sortOrder);
    NewsDto findById(Long newsId);
    NewsDto save(final News news);
    NewsDto update(final NewsDto newsDto);
    NewsDto update(final Long orderId, final News news);
    void deleteById(final Long newsId);
    Boolean existsByOrderId(Long newsId);
}
