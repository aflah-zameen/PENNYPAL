package com.application.pennypal.application.port.in.user;

import com.application.pennypal.application.dto.output.user.DashboardOutputModel;

public interface GetDashboardSummary {
    DashboardOutputModel execute(String userId);
}
