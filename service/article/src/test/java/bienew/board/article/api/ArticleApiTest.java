package bienew.board.article.api;

import bienew.board.article.service.request.ArticleCreateRequest;
import bienew.board.article.service.request.ArticleUpdateRequest;
import bienew.board.article.service.response.ArticlePageResponse;
import bienew.board.article.service.response.ArticleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.Set;

public class ArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest() {
        ArticleCreateRequest[] data = {
                new ArticleCreateRequest(
                        "hello", "world",
                        Set.of(93223085430833152L, 93223086101921792L),
                        93220295450034176L
                ),
                new ArticleCreateRequest(
                        "hello2", "world2",
                        Set.of(93223086324219904L, 93223086538129408L),
                        93220296737685504L
                ),
        };

        Arrays.stream(data).forEach(
                request -> {
                    ArticleResponse response = restClient.post()
                            .uri("/v1/articles")
                            .body(request)
                            .retrieve()
                            .body(ArticleResponse.class);

                    System.out.println("response = " + response);
                }
        );
    }

    @Test
    void readTest() {
        ArticleResponse response = restClient.get()
                .uri("/v1/articles/{articleId}", 93342858258096128L)
                .retrieve()
                .body(ArticleResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void updateBasicTest() {
        ArticleResponse response = restClient.put()
                .uri("/v1/articles/{articleId}", 93342858258096128L)
                .body(new ArticleUpdateRequest(
                        "hello1", "world1",
                        Set.of(93223085430833152L, 93223086101921792L),
                        93220295450034176L
                ))
                .retrieve()
                .body(ArticleResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void updateArticleTagListTest() {
        ArticleResponse response = restClient.put()
                .uri("/v1/articles/{articleId}", 93342858258096128L)
                .body(new ArticleUpdateRequest(
                        "hello1", "world1",
                        Set.of(93223086324219904L, 93223086101921792L),
                        93220295450034176L
                ))
                .retrieve()
                .body(ArticleResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void updateArticleSeriesTest() {
        ArticleResponse response = restClient.put()
                .uri("/v1/articles/{articleId}", 93342858258096128L)
                .body(new ArticleUpdateRequest(
                        "hello1", "world1",
                        Set.of(93223086324219904L, 93223086101921792L),
                        93220297203253248L
                ))
                .retrieve()
                .body(ArticleResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void addDummyDataTest() {
        ArticleCreateRequest[] data = {
                new ArticleCreateRequest(
                        "hello", "world",
                        Set.of(93223085430833152L, 93223086101921792L),
                        93220295450034176L
                ),
                new ArticleCreateRequest(
                        "hello2", "world2",
                        Set.of(93223086324219904L, 93223086538129408L),
                        93220296737685504L
                ),
        };
        for (int i = 0; i < 100; i++) {
            ArticleResponse response = restClient.post()
                    .uri("v1/articles")
                    .body(new ArticleCreateRequest(
                            "hello" + (i + 1), "world2" + (i + 1),
                            Set.of(93223086324219904L, 93223086538129408L),
                            93220296737685504L
                    ))
                    .retrieve()
                    .body(ArticleResponse.class);
        }
    }

    @Test
    void readPageTest() {
        ArticlePageResponse response = restClient.get()
                .uri("/v1/articles?tagId=93223086324219904&page=1&pageSize=12")
                .retrieve()
                .body(ArticlePageResponse.class);


        System.out.println("article count = " + response.articleCount());
        response.articles().forEach(System.out::println);

    }
}
