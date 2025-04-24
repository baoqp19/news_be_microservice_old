package com.example.news_service.controller;

import com.example.news_service.constant.Constants;
import com.example.news_service.dto.response.ApiResponse;
import com.example.news_service.dto.response.FootballDto;
import com.example.news_service.entities.Football;
import com.example.news_service.service.FootballService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/football")
public class FootballController {

    FootballService footballService;

    @GetMapping
    public ApiResponse<List<FootballDto>> getAlFootball() {
        return ApiResponse.<List<FootballDto>>builder()
                .code(HttpStatus.OK.value())
                .message(Constants.SUCCESS)
                .totalRecords((long) footballService.findAll().size())
                .data(footballService.findAll())
                .build();
    }

    @GetMapping("/page")
    public Page<FootballDto> getAllFootballPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        return footballService.findAll(page, size, sortBy, sortOrder);
    }

    @GetMapping("/{id}")
    public FootballDto getFootballById(@PathVariable Long id) {
        return footballService.findById(id);
    }

    @PostMapping
    public FootballDto createFootball(@RequestBody Football football) {
        return footballService.save(football);
    }

    @PutMapping("/{id}")
    public FootballDto updateFootball(@PathVariable Long id, @RequestBody Football football) {
        return footballService.update(id, football);
    }

    @DeleteMapping("/{id}")
    public void deleteFootball(@PathVariable Long id) {
        footballService.deleteById(id);
    }

}
