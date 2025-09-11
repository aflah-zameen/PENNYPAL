package com.application.pennypal.interfaces.rest.dtos.lent;

public record LoanFilterDto(
        String searchTerm,
        String status,
        String sortOrder
) {
}
