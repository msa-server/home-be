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

    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true)
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
        articles.add(article);
        articleCount += 1;
    }

    public void increaseCount() { this.articleCount += 1; }
    public void decreaseCount() { this.articleCount -= 1; }
}
