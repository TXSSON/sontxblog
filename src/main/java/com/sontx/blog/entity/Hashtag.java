package com.sontx.blog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
@Table(name = "hashtags")
public class Hashtag extends BaseModel {
    String name;
    String slug;

    @ManyToMany(mappedBy = "hashtags")
    @Builder.Default
    Set<Post> posts = new HashSet<>();
}
