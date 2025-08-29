package bienew.board.article.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Table(
        name = "article_tag",
        uniqueConstraints = @UniqueConstraint(columnNames = {"article_id", "tag_id"})
)
@EqualsAndHashCode
public class ArticleTag {
    @Id
    @GeneratedValue
    private Long articleTagId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public ArticleTag(Article article, Tag tag) {
        this.article = article;
        this.tag = tag;
    }
}
