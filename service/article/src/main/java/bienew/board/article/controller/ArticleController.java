package bienew.board.article.controller;


import bienew.board.article.service.ArticleService;
import bienew.board.article.service.request.ArticleCreateRequest;
import bienew.board.article.service.request.ArticleUpdateRequest;
import bienew.board.article.service.response.ArticleCountResponse;
import bienew.board.article.service.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/v1/articles/{articleId}")
    public ArticleResponse read(@PathVariable Long articleId) {
        return articleService.read(articleId);
    }

    @GetMapping("/v1/articles")
    public List<ArticleResponse> readAll(
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ) {
        return articleService.readAll(page, pageSize);
    }

    @GetMapping("/v1/articles/count")
    public ArticleCountResponse readAllArticleCount() {
        return articleService.getTotalArticleCount();
    }

    @PostMapping("/v1/articles")
    public ArticleResponse create(@RequestBody ArticleCreateRequest request) {
        return articleService.create(request);
    }

    @PutMapping("/v1/articles/{articleId}")
    public ArticleResponse update(
            @PathVariable Long articleId,
            @RequestBody ArticleUpdateRequest request) {
        return articleService.update(articleId, request);
    }

}
