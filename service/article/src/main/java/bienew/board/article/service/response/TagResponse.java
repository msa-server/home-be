package bienew.board.article.service.response;

import bienew.board.article.entity.Tag;

public record TagResponse(
        String tagId,
        String tagName,
        Long count
) {
    public static TagResponse from(Tag tag) {
        return new TagResponse(
                String.valueOf(tag.getTagId()),
                tag.getTagName(),
                tag.getCount()
        );
    }
}
