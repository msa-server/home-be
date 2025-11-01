package bienew.board.article.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "series")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Series {
    @Id
    private Long seriesId;

    private String seriesName;

    private Long articleCount;

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL)
    private List<Article> articles = new ArrayList<>();

    public static Series create(Long id, String name) {
        Series series = new Series();

        series.seriesId = id;
        series.seriesName = name;
        series.articleCount = 0L;

        return series;
    }

    public void update(String name) {
        this.seriesName = name;
    }

    public void addArticle(Article article) {
        if (article == null) {
            return;
        }

        // 이미 해당 시리즈에 존재 하는 경우 패스
        if (articles.contains(article)) {
            return;
        }

        // 이미 다른 시리즈에 있는 경우 빼고 여기에 추가.
        if (article.getSeries() != null && article.getSeries() != this) {
            article.getSeries().removeArticle(article);
        }

        this.articles.add(article);
        article.setSeries(this);
        articleCount++;
    }

    public void removeArticle(Article article) {
        if (article == null) {
            return;
        }

        if (articles.remove(article)) {
            articleCount--;
        }
    }
}
