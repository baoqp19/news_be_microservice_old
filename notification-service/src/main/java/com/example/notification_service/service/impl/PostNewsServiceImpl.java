package com.example.notification_service.service.impl;

import com.example.notification_service.dto.PostNewsDto;
import com.example.notification_service.entity.PostNews;
import com.example.notification_service.helper.PostNewsMappingHelper;
import com.example.notification_service.repository.PostNewsRepository;
import com.example.notification_service.service.PostNewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class PostNewsServiceImpl implements PostNewsService {

    private final PostNewsRepository postNewsRepository;

    @Autowired
    public PostNewsServiceImpl(PostNewsRepository paymentRepository) {
        this.postNewsRepository = paymentRepository;
    }

    @Override
    public Mono<PostNews> savePostNews(PostNewsDto postNewsDto) {
        return Mono.fromSupplier(() -> postNewsRepository.save(PostNewsMappingHelper.map(postNewsDto)))
                .onErrorResume(throwable -> {
                    log.error("Error saving payment: {}", throwable.getMessage());
                    return Mono.error(throwable);
                });
    }

    @Override
    public Mono<PostNews> getPostNews(Long postId) {
        return Mono.fromSupplier(() -> postNewsRepository.findById(postId)
                        .orElse(null));
    }

    @Override
    public Mono<List<PostNews>> getAllPostNews() {
        return Mono.fromSupplier(postNewsRepository::findAll)
                .onErrorResume(throwable -> {
                    log.error("Error fetching user info: {}", throwable.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Mono<List<PostNews>> getAllPostNewsByUserId(Long userId) {
        return Mono.fromSupplier(() -> postNewsRepository.findByUserId(userId))
                .onErrorResume(throwable -> {
                    log.error("Error fetching user info: {}", throwable.getMessage());
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Void> deletePostNews(Long paymentId) {
        log.info("Void, service; delete payment by id");
        return Mono.fromRunnable(() -> postNewsRepository.deleteById(paymentId));
    }

    @Override
    public Mono<Void> deleteAllPostNews() {
        return Mono.fromRunnable(postNewsRepository::deleteAll);
    }
}
