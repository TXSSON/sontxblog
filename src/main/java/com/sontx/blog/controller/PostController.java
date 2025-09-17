package com.sontx.blog.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sontx.blog.dto.request.PostRequest;
import com.sontx.blog.dto.response.ApiResponse;
import com.sontx.blog.dto.response.PostResponse;
import com.sontx.blog.service.PostService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PostController {

    PostService postService;
    
    @PostMapping
    public ApiResponse<PostResponse> createPost( @Valid @RequestBody  PostRequest request) {
       return ApiResponse.<PostResponse>builder()
            .code(1000)
            .message("Create post successfully")
            .result(postService.createPost(request))
            .build();
    }

    @GetMapping("/{slug}")
    public ApiResponse<PostResponse> getPostBySlug(@PathVariable("slug") String slug) {
        return ApiResponse.<PostResponse>builder()
            .code(1000)
            .message("Get post successfully")
            .result(postService.getPostBySlug(slug))
            .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<?> deletePost(Long id) {
        postService.deletePost(id);
        return ApiResponse.builder()
            .code(1000)
            .message("Delete post successfully")
            .build();
    }
    @GetMapping("/hashtag/{slug}")
    public ApiResponse<Set<PostResponse>> getPostByHashtagSlug(String slug) {
        return ApiResponse.<Set<PostResponse>>builder()
            .code(1000)
            .message("Get posts by hashtag successfully")
            .result(postService.getPostsByHashtagSlug(slug))
            .build();
    }
}
