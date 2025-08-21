package bienew.board.article.service.request;


import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ArticleCreateRequest {
    private String title;
    private String content;
    private List<Long> tagIds;
}
