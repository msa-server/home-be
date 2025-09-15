package bienew.board.article.service.response;

import bienew.board.article.entity.Series;

public record SeriesResponse(
        Long seriesId,
        String seriesName,
        Long articleCount
) {
    public static SeriesResponse from(Series series) {
        return new SeriesResponse(
                series.getSeriesId(),
                series.getSeriesName(),
                series.getArticleCount()
        );
    }
}
