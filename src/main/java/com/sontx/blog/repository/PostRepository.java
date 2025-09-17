package com.sontx.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sontx.blog.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Optional<Post> findById(long id);
    Post findBySlug(String slug);
    boolean existsByTitle(String title);
}   
