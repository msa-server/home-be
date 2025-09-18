package bienew.board.article.repository;

import bienew.board.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query(
            value = "select article_id, title, content," +
                    " created_at, modified_at, series_id " +
                    "from article " +
                    "order by article_id desc " +
                    "limit :limit offset :offset",
//            value = "select article.article_id, article.title, article.content," +
//                    " article.created_at, article.modified_at, article.series_id " +
//                    "from article(" +
//                    "   select article_id from article_tag" +
//                    "   where tag_id = :tagId" +
//                    "   order by article_id desc" +
//                    "   limit :limit offset :offset" +
//                    ") t left join article on t.article_id = article.article_id",
            nativeQuery = true
    )
    List<Article> findAll(
            @Param("offset") Long offset,
            @Param("limit") Long limit);
}
