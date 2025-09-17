package com.sontx.blog.service;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sontx.blog.dto.request.PostRequest;
import com.sontx.blog.dto.response.PostResponse;
import com.sontx.blog.entity.Category;
import com.sontx.blog.entity.Hashtag;
import com.sontx.blog.entity.Media;
import com.sontx.blog.entity.Post;
import com.sontx.blog.exception.AppException;
import com.sontx.blog.exception.ErrorCode;
import com.sontx.blog.mapper.PostMapper;
import com.sontx.blog.repository.CategoryRepository;
import com.sontx.blog.repository.HashtagRepository;
import com.sontx.blog.repository.PostRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.nio.file.*;

import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostService {

    @Value("${app.upload.dir}")
    @NonFinal
    private String uploadDir;

    PostRepository postRepository;
    PostMapper postMapper;
    CategoryRepository categoryRepository;
    HashtagRepository hashtagRepository;

    public PostResponse createPost(PostRequest request) {

        if (postRepository.existsByTitle(request.getTitle())) {
            throw new AppException(ErrorCode.POST_TITlE_EXISTED);
        }

        Category category = categoryRepository.findByName(request.getCategoryName());
        if (category == null) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        Set<Hashtag> hashtags = hashtagRepository.findAllByNameIn(request.getHashtags());
        for (String name : request.getHashtags()) {
            if (hashtags.stream().map(Hashtag::getName).collect(Collectors.toSet()).contains(name) == false) {
                Hashtag hashtag = Hashtag.builder().name(name).build();
                hashtagRepository.save(hashtag);
                hashtags.add(hashtag);
            }
        }
        Set<Media> medias = new HashSet<>();
        if (request.getMedias() != null){
            medias = extractMetaDataMedia(request.getMedias());
        }
        PostResponse postResponse = new PostResponse();
        Post post = new Post();
        post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .category(category)
                .hashtags(hashtags)
                .media(medias)
                .build();
        postRepository.save(post);
        return postResponse;
    }

    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_EXISTED));
        return postMapper.toResponse(post);
    }

    public Set<PostResponse> getPostsByHashtagSlug(String slug) {
        Hashtag hashtag = hashtagRepository.findBySlug(slug);
        if (hashtag == null) {
            throw new AppException(ErrorCode.HASHTAG_NOT_EXISTED);
        }
        Set<Post> posts = hashtag.getPosts();
        if (posts.isEmpty()) {
            throw new AppException(ErrorCode.POST_NOT_EXISTED);
        }
       
        return posts.stream()
                .map(new Function<Post, PostResponse>() {
                    @Override
                    public PostResponse apply(Post post) {
                        return postMapper.toResponse(post);
                    }
                })
                .collect(Collectors.toSet());
    }
    
    public Set<PostResponse> getPostsByCategorySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug);
        if (category == null) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        Set<Post> posts = category.getPosts();
        if (posts.isEmpty()) {
            throw new AppException(ErrorCode.POST_NOT_EXISTED);
        }
       
        return posts.stream()
                .map(new Function<Post, PostResponse>() {
                    @Override
                    public PostResponse apply(Post post) {
                        return postMapper.toResponse(post);
                    }
                })
                .collect(Collectors.toSet());
    }

    public PostResponse getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug);
        if (post == null) {
            throw new AppException(ErrorCode.POST_NOT_EXISTED);
        }
        return postMapper.toResponse(post);
    }

    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_EXISTED));
        postRepository.delete(post);
    }

    public PostResponse updatePost(Long id, PostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_EXISTED));
        Category category = categoryRepository.findByName(request.getCategoryName());
        if (category == null) {
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        Set<Hashtag> hashtags = hashtagRepository.findAllByNameIn(request.getHashtags());
        for (String name : request.getHashtags()) {
            if (hashtags.stream().map(Hashtag::getName).collect(Collectors.toSet()).contains(name) == false) {
                Hashtag hashtag = Hashtag.builder().name(name).build();
                hashtagRepository.save(hashtag);
                hashtags.add(hashtag);
            }
        }
        Set<Media> medias = extractMetaDataMedia(request.getMedias());
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCategory(category);
        post.setHashtags(hashtags);
        post.setMedia(medias);
        postRepository.save(post);
        return postMapper.toResponse(post);
    }

    private Set<Media> extractMetaDataMedia(Set<MultipartFile> files) {
        Set<Media> medias = new HashSet<Media>();
        for (MultipartFile file : files){
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);

                Media media = new Media();
                media.setName(fileName);
                media.setUrl(filePath.toString());
                media.setSize(file.getSize());
                media.setType(file.getContentType());
                medias.add(media);
                saveFile(file, filePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return medias; 
    }

    private void saveFile(MultipartFile file, Path filePath) {
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
