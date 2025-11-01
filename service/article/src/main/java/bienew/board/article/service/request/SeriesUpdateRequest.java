package bienew.board.article.service.request;

import jakarta.validation.constraints.NotNull;

public record SeriesUpdateRequest(
        @NotNull String seriesName
) {
}
