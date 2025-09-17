package com.sontx.blog.entity;

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
@Table(name = "media")
public class Media extends BaseModel {
    String url; 
    String name; 
    Long size;
    String type;
    @ManyToMany(mappedBy = "media")
    Set<Post> posts;
}
