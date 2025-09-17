package com.sontx.blog.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sontx.blog.entity.Category;
import com.sontx.blog.dto.request.CategoryRequest;
import com.sontx.blog.dto.response.CategoryResponse;


@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "slug", ignore = true)
    Category toEntity(CategoryRequest request);
    CategoryResponse toResponse(Category category);
}

