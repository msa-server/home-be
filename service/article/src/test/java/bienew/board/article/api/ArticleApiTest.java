package bienew.board.article.api;

import bienew.board.article.service.request.ArticleCreateRequest;
import bienew.board.article.service.request.ArticleUpdateRequest;
import bienew.board.article.service.response.ArticlePageResponse;
import bienew.board.article.service.response.ArticleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class ArticleApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest() {
        ArticleResponse response = restClient.post()
                .uri("/v1/articles")
                .body(new ArticleCreateRequest(
                        "test1",
                        "test2",
                        List.of()
                ))
                .retrieve()
                .body(ArticleResponse.class);

        System.out.println("response : " + response);
    }

    @Test
    void readTest() {
        ArticleResponse response = restClient.get()
                .uri("/v1/articles/{articleId}", 84207313704575020L)
                .retrieve()
                .body(ArticleResponse.class);

        System.out.println("response = " + response);
    }

    @Test
    void readAllTest() {
        ArticlePageResponse responses = restClient.get()
                .uri("/v1/articles?tagId=84186626948456448&pageSize=10&page=5000")
                .retrieve()
                .body(ArticlePageResponse.class);

        responses.articles().stream().forEach(System.out::println);
    }

    @Test
    void updateTest() {
        ArticleResponse response = restClient.put()
                .uri("/v1/articles/{articleId}", 86618720791166976L)
                .body(new ArticleUpdateRequest(
                        "test_update",
                        "test_update",
                        List.of(84186626948456448L,
                                84183869777043456L,
                                84183869856735232L)
                ))
                .retrieve()
                .body(ArticleResponse.class);

        System.out.println("response : " + response);
    }

}
