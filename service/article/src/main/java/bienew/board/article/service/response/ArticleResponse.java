package bienew.board.article.service.response;

import bienew.board.article.entity.Article;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleResponse(
        String articleId,
        String title,
        String content,
        List<TagResponse> articleTags,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        SeriesResponse series
) {

    public static ArticleResponse from(Article article) {
        return new ArticleResponse(
                String.valueOf(article.getArticleId()),
                article.getTitle(),
                article.getContent(),
                article.getArticleTags().stream().map(at -> TagResponse.from(at.getTag())).toList(),
                article.getCreatedAt(),
                article.getModifiedAt(),
                SeriesResponse.from(article.getSeries())
        );
    }
}
