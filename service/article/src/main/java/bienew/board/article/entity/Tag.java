package bienew.board.article.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Table(name = "tag")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
    @Id
    private Long tagId;

    @Column(unique = true, nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleTag> articleTags = new ArrayList<>();

    public static Tag create(Long id, String tagName) {
        Tag tag = new Tag();

        tag.tagId = id;
        tag.tagName = tagName;

        return tag;
    }
}
