package com.application.pennypal.application.output.paged;

import java.util.List;

public record PagedResultOutput<T>(List <T> content, int pageNumber,
                                   int pageSize, long totalElements, int totalPages) {
}
