package com.sontx.blog.mapper;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
    List<com.sontx.blog.dto.response.HashtagResponse> toResponse(List<com.sontx.blog.entity.Hashtag> hashtags);
}
