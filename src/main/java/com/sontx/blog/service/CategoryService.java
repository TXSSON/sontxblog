package com.sontx.blog.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sontx.blog.dto.request.CategoryRequest;
import com.sontx.blog.dto.response.CategoryResponse;
import com.sontx.blog.entity.Category;
import com.sontx.blog.exception.ErrorCode;
import com.sontx.blog.mapper.CategoryMapper;
import com.sontx.blog.repository.CategoryRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import com.sontx.blog.exception.AppException;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public void createCategory(CategoryRequest request) {
        if(categoryRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.CATEGORY_EXISTED);
        }
        categoryRepository.save(categoryMapper.toEntity(request));
        
    }
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

    public void updateCategory(int id, CategoryRequest request) {
        Category category = categoryRepository.findById(id);
        if(category == null){
            throw new AppException(ErrorCode.CATEGORY_NOT_EXISTED);
        }
        category.setName(request.getName());
        category.setParentId(request.getParentId());
        category.setSortOrder(request.getSortOrder());
        categoryRepository.save(category);
    }

    public Set<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toSet());
    }
}
