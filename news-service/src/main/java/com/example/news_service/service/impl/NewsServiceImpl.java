package com.example.news_service.service.impl;

import com.example.news_service.dto.response.NewsDto;
import com.example.news_service.entities.News;
import com.example.news_service.exception.EnumConfig.ErrorCode;
import com.example.news_service.exception.payload.AppException;
import com.example.news_service.helper.NewsMappingHelper;
import com.example.news_service.repository.NewsRepository;
import com.example.news_service.service.NewsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NewsServiceImpl implements NewsService {

    NewsRepository newsRepository;

    @Override
    public List<NewsDto> findAll() {
        return newsRepository.findAll()
                .stream()
                .map(NewsMappingHelper::map)
                .collect(Collectors.toList());
    }

    @Override
    public Page<NewsDto> findAll(int page, int size, String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Page<News> newsPage = newsRepository.findAll(PageRequest.of(page, size, Sort.by(direction, sortBy)));
        return newsPage.map(NewsMappingHelper::map);
    }


    @Override
    public List<NewsDto> findByCategory(String category) {
        List<News> result = newsRepository.findByCategory(category);
        try {
            return result
                    .stream()
                    .map(NewsMappingHelper::map)
                    .collect(Collectors.toList());
        } catch (Exception e){
            throw new AppException(ErrorCode.NOT_FOUND);
        }
    }

    @Override
    public Page<NewsDto> findByCategory(String category, int page, int size, String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Page<News> newsPage;
        if (sortBy != null && !sortBy.isEmpty()) {
            newsPage = newsRepository.findByCategory(category, PageRequest.of(page, size, Sort.by(direction, sortBy)));
        } else {
            newsPage = newsRepository.findByCategory(category, PageRequest.of(page, size, direction, "defaultSortField"));
        }
        return newsPage.map(NewsMappingHelper::map);
    }

    @Override
    public NewsDto findById(Long newsId) {
        return newsRepository.findById(newsId)
                .map(NewsMappingHelper::map)
                .orElse(null);
    }

    @Override
//    @PreAuthorize("hasRole('ADMIN')")
    public NewsDto save(News news) {
        try {
            newsRepository.save(news);
            return NewsMappingHelper.map(news);
        } catch (DataAccessException e) {
            throw new AppException(ErrorCode.DATABASE_ERROR);
        }
    }

    @Override
    public NewsDto update(NewsDto newsDto) {
        return null;
    }

    @Override
    public NewsDto update(Long id, News news) {
        News news1 = newsRepository.getById(id);
        news1.setTitle(news.getTitle());
        news1.setImg(news.getImg());
        news1.setCategory(news.getCategory());
        news1.setLink(news.getLink());
        news1.setPubDate(news.getPubDate());

        return NewsMappingHelper.map(newsRepository.save(news1));
    }

    @Override
    public void deleteById(Long newsId) {
        newsRepository.deleteById(newsId);
    }

    @Override
    public Boolean existsByOrderId(Long newsId) {
        return newsRepository.existsById(newsId);
    }

}

