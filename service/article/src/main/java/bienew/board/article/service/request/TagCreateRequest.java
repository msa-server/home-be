package bienew.board.article.service.request;

import jakarta.validation.constraints.NotBlank;

public record TagCreateRequest(
        @NotBlank String tagName
) { }
