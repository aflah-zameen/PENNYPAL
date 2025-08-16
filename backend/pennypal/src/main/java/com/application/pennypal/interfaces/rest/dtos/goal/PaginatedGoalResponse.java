package com.application.pennypal.interfaces.rest.dtos.goal;

import java.util.List;

public record PaginatedGoalResponse(
        List<GoalAdminResponseDTO> data,
        PaginationInfo pagination
) {
}
