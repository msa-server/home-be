package bienew.board.article.service.response;

import java.util.List;

public record ArticlePageResponse(
        List<ArticleResponse> articles,
        Long articleCount
) {
    public static ArticlePageResponse of(List<ArticleResponse> data, Long cnt) {
        return new ArticlePageResponse(data, cnt);
    };
}
