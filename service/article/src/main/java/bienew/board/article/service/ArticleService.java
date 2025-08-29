package bienew.board.article.service;


import bienew.board.article.entity.Article;
import bienew.board.article.repository.ArticleRepository;
import bienew.board.article.repository.TagRepository;
import bienew.board.article.service.request.ArticleCreateRequest;
import bienew.board.article.service.request.ArticleUpdateRequest;
import bienew.board.article.service.response.ArticlePageResponse;
import bienew.board.article.service.response.ArticleResponse;
import bienew.common.snowflake.Snowflake;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final Snowflake snowflake = new Snowflake();

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    /**
     * 게시글 생성
     */
    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        Article article = Article.create(
                snowflake.nextId(),
                request.title(),
                request.content());

        for (Long tagId: request.tagIds()) {
            article.addTag(
                    tagRepository.findById(tagId)
                            .orElseThrow()
            );
        }
        articleRepository.save(article);

        return ArticleResponse.from(article);
    }

    /**
     * 해당 게시글 상세 조회
     */
    public ArticleResponse read(Long articleId) {
        return ArticleResponse.from(articleRepository.findById(articleId).orElseThrow());
    }

    /**
     * 해당 태그를 포함하는 게시글 조회.
     */
    public ArticlePageResponse readAll(Long tagId, Long page, Long pageSize) {
        return ArticlePageResponse.of(
                articleRepository.findAll(tagId,(page - 1) * pageSize, pageSize).stream()
                        .map(ArticleResponse::from).toList(),
                articleRepository.count(
                        tagId,
                        PageLimitCalculator.calculatorPageLimit(page, pageSize, 12L)
                )
        );
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow();

        article.update(request.title(), request.content(),
                request.tagIds().isEmpty()
                        ? List.of()
                        : tagRepository.findByTagIdIn(request.tagIds())
                );
        return ArticleResponse.from(article);
    }
}
