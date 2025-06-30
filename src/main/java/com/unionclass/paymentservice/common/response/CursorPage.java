package com.unionclass.paymentservice.common.response;

import java.util.List;
import java.util.function.Function;

public record CursorPage<T>(List<T> content, String nextCursor, String prevCursor,
                            Boolean hasNext, Boolean hasPrevious, int size, Integer page, Integer totalPages) {

    public static <T> CursorPage<T> of(
            List<T> content, String nextCursor, String prevCursor,
            Boolean hasNext, Boolean hasPrevious, int size, Integer page, Integer totalPages
    ) {
        return new CursorPage<>(content, nextCursor, prevCursor, hasNext, hasPrevious, size, page, totalPages);
    }

    public <R> CursorPage<R> map(Function<T,R> mapper) {

        return CursorPage.of(
                content().stream().map(mapper).toList(), nextCursor, prevCursor,
                hasNext, hasPrevious, size, page, totalPages
        );
    }
}
