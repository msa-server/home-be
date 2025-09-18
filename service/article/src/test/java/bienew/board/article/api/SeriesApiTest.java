package bienew.board.article.api;

import bienew.board.article.service.request.SeriesCreateRequest;
import bienew.board.article.service.request.SeriesUpdateRequest;
import bienew.board.article.service.response.SeriesResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

public class SeriesApiTest {
    RestClient restClient = RestClient.create("http://localhost:9000");

    @Test
    void createTest() {
        String[] data = {"DB", "SQL", "SI", "CS"};

        Arrays.stream(data).forEach(
                title -> {
                    SeriesResponse response = restClient.post()
                            .uri("/v1/series")
                            .body(new SeriesCreateRequest(title))
                            .retrieve()
                            .body(SeriesResponse.class);

                    System.out.println("response = " + response);
                }
        );
    }

    @Test
    void readTest() {
        List<SeriesResponse> list = restClient.get()
                .uri("/v1/series")
                .retrieve()
                .body(new ParameterizedTypeReference<List<SeriesResponse>>() {
                });

        list.forEach(System.out::println);
    }

    @Test
    void updateTest() {
        SeriesResponse response = restClient.put()
                .uri("/v1/series/{seriesId}", 93220295450034176L)
                .body(new SeriesUpdateRequest("DB-MASTER"))
                .retrieve()
                .body(SeriesResponse.class);

        System.out.println("response =" + response);
    }

}
