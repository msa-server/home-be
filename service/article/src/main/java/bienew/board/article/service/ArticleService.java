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
import bienew.board.article.service.response.ArticleCountResponse;
import bienew.board.article.service.response.ArticleResponse;
import bienew.board.article.service.response.SeriesResponse;
import bienew.board.article.service.response.TagResponse;
import bienew.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

        System.out.println("new article = " + article);

        // 2. 태그 추가
        addTagsToArticle(article, request.tagIds());

        // 3. 시리즈 설정
        Series series = seriesRepository.findById(request.seriesId())
                .orElseThrow();
        series.addArticle(article);

        return ArticleResponse.from(article);
    }

    /**
     * 해당 게시글 상세 조회
     */
    @Transactional
    public ArticleResponse read(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();

        return ArticleResponse.from(article);
    }

    /**
     * 특정 페이지 게시글 목록 조회.
     */
    @Transactional
    public List<ArticleResponse> readAll(Long page, Long pageSize) {
        return articleRepository.getPagedArticles((page - 1) * pageSize, pageSize).stream()
                .map(ArticleResponse::from).toList();
    }

    @Transactional
    public ArticleResponse update(Long articleId, ArticleUpdateRequest request) {
        Article article = articleRepository.findById(articleId).orElseThrow();

        // 1. 태그 리스트 업데이트
        // 1-1 기존 태그 Id 리스트 추출
        Set<Long> nowTags = article.getArticleTags().stream()
                .map(articleTag -> articleTag.getTag().getTagId())
                .collect(Collectors.toSet());

        // 1-2 새로은 태그 Id 리스트 추출
        Set<Long> newTags = new HashSet<>(request.tagIds());

        // 1-3 제거된 태그 처리 (기존 O, 신규 X)
        article.getArticleTags().removeIf(articleTag -> {
            Tag tag = articleTag.getTag();

            if (!newTags.contains(tag.getTagId())) {
                tag.decrease();
                return true;
            }

            return false;
        });

        // 1-4 추가할 태그 처리 (기존 X, 신규 O)
        newTags.removeAll(nowTags);

        if (!newTags.isEmpty()) {
            addTagsToArticle(article, newTags);
        }

        // 2. 게시글 내용 업데이트
        article.update(request.title(), request.content());

        // 3. 시리즈 수정
        Series series = seriesRepository.findById(request.seriesId()).orElseThrow();
        series.addArticle(article);

        return ArticleResponse.from(article);
    }

    /**
     * 게시글에 대한 태그 추가 및 증가.
     */
    private void addTagsToArticle(Article article, Set<Long> tags) {
        if (tags == null || tags.isEmpty()) {
            return;
        }

        for (Long tagId : tags) {
            Tag tag = tagRepository.findById(tagId).orElseThrow();

            tag.increase();

            article.getArticleTags().add(ArticleTag.create(article, tag));
        }
    }

    public ArticleCountResponse getTotalArticleCount() {
        return ArticleCountResponse.from(articleRepository.count());
    }
}
