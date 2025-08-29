package bienew.board.article.service.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ArticleUpdateRequest(
        @NotNull String title,
        @NotNull String content,
        @NotNull List<Long> tagIds
) { }
