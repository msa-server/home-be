package bienew.board.article.service.response;

import bienew.board.article.entity.Tag;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TagResponse {
    private Long tagId;
    private String tagName;

    public static TagResponse from(Tag tag) {
        TagResponse response = new TagResponse();

        response.tagId = tag.getTagId();
        response.tagName = tag.getTagName();

        return response;
    }
}
