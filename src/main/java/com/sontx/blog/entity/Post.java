package com.sontx.blog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "posts")
public class Post extends BaseModel {
    String title;
    String content;
    String featuredImageUrl;
    String slug;
    @Builder.Default()
    String status = "published";

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToMany
    @JoinTable(name = "post_hashtag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "hashtag_id")
    )
    @Builder.Default
    Set<Hashtag> hashtags = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "post_media",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "media_id")
    )
    @Builder.Default
    Set<Media> media = new HashSet<>();
}
