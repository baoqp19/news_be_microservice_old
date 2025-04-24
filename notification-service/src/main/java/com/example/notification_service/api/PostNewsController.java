package com.example.notification_service.api;

import com.example.notification_service.dto.PostNewsDto;
import com.example.notification_service.entity.PostNews;
import com.example.notification_service.service.PostNewsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PostNewsController {

    PostNewsService postNewsService;

    @PostMapping
    public Mono<PostNews> savePayment(@RequestBody PostNewsDto postNewsDto) {
        return postNewsService.savePostNews(postNewsDto);
    }

    @GetMapping("/{postId}")
    public Mono<PostNews> getPayment(@PathVariable Long postId) {
        return postNewsService.getPostNews(postId);
    }

    @GetMapping
    public Mono<List<PostNews>> getAllPayments() {
        return postNewsService.getAllPostNews();
    }

    @GetMapping("/all/{userId}")
    public Mono<List<PostNews>> getAllPostNewsByUserId(@PathVariable Long userId) {
        return postNewsService.getAllPostNewsByUserId(userId);
    }

    @DeleteMapping("/{postId}")
    public Mono<Void> deletePostNews(@PathVariable Long postId) {
        return postNewsService.deletePostNews(postId);
    }

    @DeleteMapping("/all")
    public Mono<Void> deleteAllPostNews() {
        return postNewsService.deleteAllPostNews();
    }

}