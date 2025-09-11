package com.application.pennypal.application.dto.input.wallet;

public record ExportOptions(
        String format,
        boolean includeCharts,
        DateRange dateRange
) {
}
