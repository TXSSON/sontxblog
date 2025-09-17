package com.sontx.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sontx.blog.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, String>{
    Category findByName(String name);
    Category findBySlug(String slug);
    Category findById(int id);
    void deleteById(int id);
    boolean existsByName(String name);
}
