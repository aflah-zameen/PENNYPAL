package com.application.pennypal.application.port.in.lent;

public record LoanFilterInputModel( String searchTerm,
                                    String status,
                                    String sortOrder) {
}
