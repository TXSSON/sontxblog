package com.sontx.blog.mapper;

import org.mapstruct.Mapper;

import com.sontx.blog.dto.response.PostResponse;


@Mapper(componentModel = "spring")
public interface PostMapper {
    PostResponse toResponse(com.sontx.blog.entity.Post post);
}
