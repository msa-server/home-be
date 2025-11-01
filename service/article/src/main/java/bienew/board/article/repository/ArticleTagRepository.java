package bienew.board.article.repository;

import bienew.board.article.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {
    @Query(
            value = "select tag_id from article_tag where article_id = :articleId",
            nativeQuery = true
    )
    List<Long> findTagIdsByArticleId(Long articleId);

    @Modifying
    @Query(
            value = "delete from article_tag where article_id = :articleId " +
                    "and tag_id in :tagIds",
            nativeQuery = true
    )
    int deleteByArticleIdAndTagIdIn(Long articleId, Set<Long> tagIds);
}
