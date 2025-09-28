package bienew.board.article.api;

import bienew.board.article.service.request.TagCreateRequest;
import bienew.board.article.service.request.TagUpdateRequest;
import bienew.board.article.service.response.ArticleResponse;
import bienew.board.article.service.response.TagResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

public class TagApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest() {
        String[] data = {"한글 테스트", "banana", "cat", "dog"};

        Arrays.stream(data).forEach(
                tagName -> {
                    TagResponse tagResponse = restClient.post()
                            .uri("/v1/tags")
                            .body(new TagCreateRequest(tagName + "1"))
                            .retrieve()
                            .body(TagResponse.class);

                    System.out.println("response = " + tagResponse);
                }
        );
    }

    @Test
    void readTest() {
        List<TagResponse> result = restClient.get()
                .uri("/v1/tags")
                .retrieve()
                .body(new ParameterizedTypeReference<List<TagResponse>>() {
                });

        result.forEach(System.out::println);
    }

    @Test
    void updateTest() {
        TagResponse response = restClient.put()
                .uri("/v1/tags/{tagId}", 93223085430833152L)
                .body(new TagUpdateRequest("apple_chip"))
                .retrieve()
                .body(TagResponse.class);

        System.out.println("response : " + response);
    }

    @Test
    void readTagArticleListTest() {
        List<ArticleResponse> responses = restClient.get()
                .uri("/v1/tags/{tagId}/articles?page=1&pageSize=12", 93223086324219904L)
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {});

        responses.forEach(System.out::println);
    }

    @Test
    void readTagByIdTest() {
        TagResponse response = restClient.get()
                .uri("/v1/tags/{tagId}", 93223086324219904L)
                .retrieve()
                .body(TagResponse.class);

        System.out.println("res = " + response);
    }
}
