package com.application.pennypal.application.port.in.sale;

import com.application.pennypal.application.dto.input.AnalyticsFilters;
import com.application.pennypal.application.dto.output.sale.PaymentStatusDTO;
import com.application.pennypal.application.dto.output.sale.SalesDataOutput;
import com.application.pennypal.application.dto.output.sale.SubscriptionAnalyticsOutput;
import com.application.pennypal.application.dto.output.sale.SubscriptionBreakdownOutput;

import java.time.LocalDate;
import java.util.List;

public interface SubscriptionAnalytics {
    SubscriptionAnalyticsOutput getAnalyticsSummary(AnalyticsFilters filters);
    List<SalesDataOutput> getSalesData(AnalyticsFilters filters);
    List<SubscriptionBreakdownOutput> getSubscriptionBreakdown(AnalyticsFilters filters);
    List<PaymentStatusDTO> getPaymentStatusData(AnalyticsFilters filters);
}
