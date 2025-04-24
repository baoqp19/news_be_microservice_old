package com.example.news_service.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class NewsDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    String title;
    String link;
    String img;
    String pubDate;
    @JsonIgnore String category;
}
