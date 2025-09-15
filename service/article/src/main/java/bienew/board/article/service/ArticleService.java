package bienew.board.article.service;


import bienew.board.article.entity.Article;
import bienew.board.article.entity.ArticleTag;
import bienew.board.article.entity.Series;
import bienew.board.article.entity.Tag;
import bienew.board.article.repository.ArticleRepository;
import bienew.board.article.repository.SeriesRepository;
import bienew.board.article.repository.TagRepository;
import bienew.board.article.service.request.ArticleCreateRequest;
import bienew.board.article.service.request.ArticleUpdateRequest;
import bienew.board.article.service.response.ArticlePageResponse;
import bienew.board.article.service.response.ArticleResponse;
import bienew.board.article.service.response.TagResponse;
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
    private final SeriesRepository seriesRepository;

    /**
     * 게시글 생성
     */
    @Transactional
    public ArticleResponse create(ArticleCreateRequest request) {
        // 1. 개체 생성.
        Article article = Article.create(
                snowflake.nextId(),
                request.title(),
                request.content());

        // 2. 태그 추가
        addTagsToArticle(article, request.tagIds());

        // 3. 시리즈 설정
        article.updateSeries(
                seriesRepository.findById(request.seriesId())
                        .orElseThrow()
        );

        articleRepository.save(article);

        return ArticleResponse.from(article, article.getArticleTags().stream()
                .map(at -> TagResponse.from(at.getTag())).toList());
    }

    /**
     * 해당 게시글 상세 조회
     */
    @Transactional
    public ArticleResponse read(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();

        return ArticleResponse.from(article, article.getArticleTags().stream()
                .map(at -> TagResponse.from(at.getTag())).toList());
    }

    /**
     * 해당 태그를 포함하는 게시글 조회.
     */
    @Transactional
    public ArticlePageResponse readAll(Long tagId, Long page, Long pageSize) {
        return ArticlePageResponse.of(
                articleRepository.findAll(tagId, (page - 1) * pageSize, pageSize).stream()
                        .map(article -> ArticleResponse.from(
                                article,
                                article.getArticleTags().stream()
                                        .map(at -> TagResponse.from(at.getTag()))
                                        .toList()
                        )).toList(),
                tagRepository.findById(tagId).orElseThrow().getCount()
        );
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow();

        // 1. 기존 태그들의 카운트 감소
        article.getArticleTags().forEach(at -> {
            at.getTag().decrease();
        });

        // 2. 기존 태그 연결 제거
        article.getArticleTags().clear();

        // 3. 게시글 내용 업데이트
        article.update(request.title(), request.content());

        // 4. 게시글 새로운 태그 증가.
        addTagsToArticle(article, request.tagIds());

        // 5. 시리즈 수정
        Series series = seriesRepository.findById(request.seriesId()).orElseThrow();

        if (series != article.getSeries()) {
            article.updateSeries(series);
        }

        return ArticleResponse.from(article, article.getArticleTags().stream()
                .map(at -> TagResponse.from(at.getTag())).toList());
    }

    /**
     * 게시글에 대한 태그 추가 및 증가.
     */
    private void addTagsToArticle(Article article, List<Long> tags) {
        if (tags == null || tags.isEmpty()) {
            return;
        }

        for (Long tagId : tags) {
            Tag tag = tagRepository.findById(tagId).orElseThrow();

            tag.increase();

            article.getArticleTags().add(ArticleTag.create(article, tag));
        }
    }
}
