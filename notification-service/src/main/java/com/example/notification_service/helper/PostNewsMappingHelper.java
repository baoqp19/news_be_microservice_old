package com.example.notification_service.helper;

import com.example.notification_service.dto.PostNewsDto;
import com.example.notification_service.entity.PostNews;

public interface PostNewsMappingHelper {
    static PostNewsDto map(PostNews postNews) {
        if (postNews == null) return null;
        return PostNewsDto.builder()
                .title(postNews.getTitle())
                .link(postNews.getLink())
                .img(postNews.getImg())
                .pubDate(postNews.getPubDate())
                .userId(postNews.getUserId())
                .build();
    }

    static PostNews map(PostNewsDto postNewsDto) {
        if (postNewsDto == null) return null;
        return PostNews.builder()
                .title(postNewsDto.getTitle())
                .link(postNewsDto.getLink())
                .img(postNewsDto.getImg())
                .pubDate(postNewsDto.getPubDate())
                .userId(postNewsDto.getUserId())
                .build();
    }
}
