package bienew.board.article.api;

import bienew.board.article.service.request.TagCreateRequest;
import bienew.board.article.service.request.TagUpdateRequest;
import bienew.board.article.service.response.TagResponse;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

public class TagApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest() {
        TagResponse tagResponse = restClient.post()
                .uri("/v1/tags")
                .body(new TagCreateRequest("새로운 태그"))
                .retrieve()
                .body(TagResponse.class);

        System.out.println("response : " + tagResponse);
    }

    @Test
    void readTest() {
        List<TagResponse> result = restClient.get()
                .uri("/v1/tags")
                .retrieve()
                .body(new ParameterizedTypeReference<List<TagResponse>>() {
                });

        result.stream()
                .filter(a -> a.tagName().equals("새로운 태그"))
                .forEach(System.out::println);
    }

    @Test
    void updateTest() {
        TagResponse response = restClient.put()
                .uri("/v1/tags/{tagId}", 86627635492818944L)
                .body(new TagUpdateRequest("new_name"))
                .retrieve()
                .body(TagResponse.class);

        System.out.println("response : " + response);
    }
}
