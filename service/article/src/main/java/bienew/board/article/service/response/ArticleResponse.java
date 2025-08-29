package bienew.board.article.service.response;

import bienew.board.article.entity.Article;
import bienew.board.article.entity.ArticleTag;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleResponse(
        Long articleId,
        String title,
        String content,
        List<TagResponse> articleTags,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    public static ArticleResponse from(Article article, List<TagResponse> tags) {
        return new ArticleResponse(
                article.getArticleId(),
                article.getTitle(),
                article.getContent(),
                tags,
                article.getCreatedAt(),
                article.getModifiedAt()
        );
    }
}
