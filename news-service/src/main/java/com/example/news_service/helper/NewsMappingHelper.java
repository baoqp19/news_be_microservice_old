package com.example.news_service.helper;

import com.example.news_service.dto.response.NewsDto;
import com.example.news_service.entities.News;

public interface NewsMappingHelper {
    static NewsDto map(News news) {
        if (news == null)
            return null;
        return NewsDto.builder()
                .title(news.getTitle())
                .link(news.getLink())
                .img(news.getImg())
                .pubDate(news.getPubDate())
                .category(news.getCategory())
                .build();
    }

    static News map(final NewsDto newsDto) {
        if (newsDto == null)
            return null;
        return News.builder()
                .title(newsDto.getTitle())
                .link(newsDto.getLink())
                .img(newsDto.getImg())
                .pubDate(newsDto.getPubDate())
                .category(newsDto.getCategory())
                .build();
    }
}
