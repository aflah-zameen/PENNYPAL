package com.application.pennypal.interfaces.rest.dtos.lent;

public record CaseRequestDTO(
        String loanId,
        String reason
) {
}
