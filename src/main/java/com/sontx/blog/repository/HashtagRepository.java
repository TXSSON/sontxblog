package com.sontx.blog.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sontx.blog.entity.Hashtag;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, String>{
    Set<Hashtag> findAllByNameIn(Set<String> names);
    Hashtag findByName(String name);
    Hashtag findBySlug(String nameSlug);
    List<Hashtag> findAll();
}
