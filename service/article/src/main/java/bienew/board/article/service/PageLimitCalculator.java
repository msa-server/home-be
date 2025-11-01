package bienew.board.article.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageLimitCalculator {
    public static Long calculatorPageLimit(Long page, Long pageSize, Long moveablePageCount) {
        return (((page - 1) / moveablePageCount) + 1) * pageSize * moveablePageCount + 1;
    }
}
