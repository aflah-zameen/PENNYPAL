package com.application.pennypal.interfaces.rest.dtos.goal;

public record PaginationInfo(
        int page,
        int pageSize,
        long total,
        int totalPages
) {
}
