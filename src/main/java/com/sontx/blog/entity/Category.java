package com.sontx.blog.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@Table(name = "categories")
public class Category extends BaseModel {
    String name;
    String slug;
    Integer parentId;
    @Builder.Default
    Boolean isActive = true;
    Integer sortOrder;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    Set<Post> posts = new HashSet<>();
}
