package com.sontx.blog.dto.request;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostRequest {
    @NotBlank(message = "TITLE_INVALID")
    @Size(max = 100, message = "TITLE_TOO_LONG")
    String title;

    @NotBlank(message = "CONTENT_INVALID")
    String content;

    @NotBlank(message = "CATEGORY_INVALID")
    String categoryName;

    Set<String> hashtags;

    Set<MultipartFile> medias;
}

