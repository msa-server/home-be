package bienew.board.article.service.response;

import bienew.board.article.entity.Tag;

public record TagResponse(
        Long tagId,
        String tagName,
        Long count
) {
    public static TagResponse from(Tag tag) {
        return new TagResponse(
                tag.getTagId(),
                tag.getTagName(),
                tag.getCount()
        );
    }
}
