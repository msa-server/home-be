package bienew.board.article.controller;

import bienew.board.article.service.SeriesService;
import bienew.board.article.service.TagService;
import bienew.board.article.service.request.SeriesCreateRequest;
import bienew.board.article.service.request.SeriesUpdateRequest;
import bienew.board.article.service.request.TagCreateRequest;
import bienew.board.article.service.request.TagUpdateRequest;
import bienew.board.article.service.response.SeriesResponse;
import bienew.board.article.service.response.TagResponse;
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

    @PutMapping("/v1/series/{seriesId}")
    public SeriesResponse update(@PathVariable Long tagId, @RequestBody SeriesUpdateRequest request) {
        return seriesService.update(tagId, request);
    }
}
