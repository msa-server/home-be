package bienew.board.article.controller;

import bienew.board.article.service.SeriesService;
import bienew.board.article.service.request.SeriesCreateRequest;
import bienew.board.article.service.request.SeriesUpdateRequest;
import bienew.board.article.service.response.ArticleResponse;
import bienew.board.article.service.response.SeriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SeriesController {
    private final SeriesService seriesService;

    @PostMapping("/v1/series")
    public SeriesResponse create(@RequestBody SeriesCreateRequest request) {
        return seriesService.create(request);
    }

    @GetMapping("/v1/series")
    public List<SeriesResponse> readAll() {
        return seriesService.readAll();
    }

    @GetMapping("/v1/series/{seriesId}")
    public List<ArticleResponse> readArticles(
            @PathVariable Long seriesId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ) {
        return seriesService.readArticles(seriesId, page, pageSize);
    }

    @PutMapping("/v1/series/{seriesId}")
    public SeriesResponse update(@PathVariable Long seriesId, @RequestBody SeriesUpdateRequest request) {
        return seriesService.update(seriesId, request);
    }
}
