package bienew.board.article.controller;

import bienew.board.article.service.TagService;
import bienew.board.article.service.request.TagCreateRequest;
import bienew.board.article.service.response.TagResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping("/v1/tags")
    public TagResponse create(@RequestBody TagCreateRequest request) {
        return tagService.create(request);
    }

    @GetMapping("/v1/tags")
    public List<TagResponse> readAll() {
        return tagService.readAll();
    }
}
