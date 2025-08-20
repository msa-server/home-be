package bienew.board.article.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "article")
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    private Long articleId;

    private String title;
    private String content;
    private Long writerId;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static Article create(Long articleId, String title, String content, Long writerId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        Article article = new Article();

        article.articleId = articleId;
        article.title = title;
        article.content = content;
        article.writerId = writerId;
        article.createdAt = LocalDateTime.now();
        article.modifiedAt = article.createdAt;

        return article;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.modifiedAt = LocalDateTime.now();
    }
}
