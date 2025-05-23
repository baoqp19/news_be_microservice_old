package com.example.notification_service.service;

import com.example.notification_service.dto.PostNewsDto;
import com.example.notification_service.entity.PostNews;
import reactor.core.publisher.Mono;

import java.util.List;

public interface PostNewsService {
    Mono<PostNews> savePostNews(PostNewsDto paymentDto);
    Mono<PostNews> getPostNews(Long paymentId);
    Mono<List<PostNews>> getAllPostNews();
    Mono<List<PostNews>> getAllPostNewsByUserId(Long userId);
    Mono<Void> deletePostNews(Long paymentId);
    Mono<Void> deleteAllPostNews();
}
