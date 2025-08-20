package bienew.board.article.repository;

import bienew.board.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query(
            value = "select article.article_id, article.title, article.content," +
                    " article.writer_id," +
                    " article.created_at, article.modified_at " +
                    "from (" +
                    "   select article_id from article" +
                    "   order by article_id desc" +
                    "   limit :limit offset :offset" +
                    ") t left join article on t.article_id = article.article_id",
            nativeQuery = true
    )
    List<Article> findAll(
            @Param("offset") Long offset,
            @Param("limit") Long limit);
}
