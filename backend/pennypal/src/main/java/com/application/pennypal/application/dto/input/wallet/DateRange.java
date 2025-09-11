package com.application.pennypal.application.dto.input.wallet;

import java.time.LocalDate;

public record DateRange(
        LocalDate start,
        LocalDate end
) {
}
