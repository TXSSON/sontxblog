package com.sontx.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sontx.blog.dto.response.HashtagResponse;
import com.sontx.blog.entity.Hashtag;
import com.sontx.blog.mapper.HashtagMapper;
import com.sontx.blog.repository.HashtagRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HashtagService {
    
    HashtagRepository hashtagRepository;
    HashtagMapper hashtagMapper;

    public List<HashtagResponse> getAllHashtag() {
        List<Hashtag> hashtag = hashtagRepository.findAll();
        return hashtagMapper.toResponse(hashtag);
       }
}
