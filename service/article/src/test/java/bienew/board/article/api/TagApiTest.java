package bienew.board.article.api;

import bienew.board.article.service.request.TagCreateRequest;
import bienew.board.article.service.request.TagUpdateRequest;
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
        String[] data = {"apple", "banana", "cat", "dog"};

        Arrays.stream(data).forEach(
                tagName -> {
                    TagResponse tagResponse = restClient.post()
                            .uri("/v1/tags")
                            .body(new TagCreateRequest(tagName))
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
}
