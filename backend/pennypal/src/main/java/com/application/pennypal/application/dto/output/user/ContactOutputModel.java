package com.application.pennypal.application.dto.output.user;

import java.util.List;

public record ContactOutputModel(
        String id,
        String name,
        String email,
        String profileURL,
        boolean isOnline,
        List<TransferTransaction> transactions
) {
}
