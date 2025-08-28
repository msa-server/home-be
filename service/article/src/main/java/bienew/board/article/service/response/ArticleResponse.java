package bienew.board.article.service.response;

import bienew.board.article.entity.Article;
import bienew.board.article.entity.ArticleTag;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleResponse(
        Long articleId,
        String title,
        String content,
        List<ArticleTag> articleTags,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static ArticleResponse from(Article article) {
        return new ArticleResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getContent(),
                article.getArticleTags(),
                article.getCreatedAt(),
                article.getModifiedAt()
        );
    }
}
