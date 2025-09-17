package com.sontx.blog.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sontx.blog.dto.request.CategoryRequest;
import com.sontx.blog.dto.response.ApiResponse;
import com.sontx.blog.dto.response.CategoryResponse;
import com.sontx.blog.service.CategoryService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    CategoryService categoryService;

    @PostMapping
    public ApiResponse<?> createCategory(@Valid @RequestBody CategoryRequest request) {
        categoryService.createCategory(request);
        return ApiResponse.<CategoryResponse>builder()
                .code(1000)
                .message("Create category successfully")
                .build();
    }

}
