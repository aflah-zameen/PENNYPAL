package com.application.pennypal.application.dto.output.sale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SubscriptionBreakdownOutput
 {
     String planId;
     Long subscriberCount;
     BigDecimal totalRevenue;
}
