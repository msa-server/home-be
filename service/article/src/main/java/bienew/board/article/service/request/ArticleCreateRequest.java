package bienew.board.article.service.request;

import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record ArticleCreateRequest(
        @NotNull String title,
        @NotNull String content,
        @NotNull Set<Long> tagIds,
        @NotNull Long seriesId
) { }
