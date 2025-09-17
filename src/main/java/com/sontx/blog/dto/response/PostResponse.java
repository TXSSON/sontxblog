package com.sontx.blog.dto.response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponse{
    String title;
    String content;
    String featuredImageUrl;
    String slug;
    CategoryResponse category;
    Set<HashtagResponse> hashtags;
}
