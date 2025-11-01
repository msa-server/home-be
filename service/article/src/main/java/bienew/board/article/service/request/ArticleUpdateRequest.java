package bienew.board.article.service.request;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ArticleUpdateRequest(
        @NotNull String title,
        @NotNull String content,
        @NotNull Set<Long> tagIds,
        @NotNull Long seriesId
) {
}
