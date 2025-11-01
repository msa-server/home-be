package bienew.board.article.service.response;

public record ArticleCountResponse (Long articleCount){
    public static ArticleCountResponse from(Long cnt) {
        return new ArticleCountResponse(cnt);
    }
}
