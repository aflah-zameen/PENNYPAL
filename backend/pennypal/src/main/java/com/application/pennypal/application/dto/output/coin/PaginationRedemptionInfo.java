package com.application.pennypal.application.dto.output.coin;

public record PaginationRedemptionInfo(
        int currentPage,
        int totalPages,
        long totalItems,
        int itemsPerPage
) {
}
