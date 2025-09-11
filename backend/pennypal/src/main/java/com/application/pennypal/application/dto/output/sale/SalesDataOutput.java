package com.application.pennypal.application.dto.output.sale;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Getter
@Setter
public class SalesDataOutput
{
    String month;
    String planId;
    String planName;
    BigDecimal revenue;
    Long transactionCount;

    public SalesDataOutput(String month,String planId,BigDecimal revenue,Long transactionCount){
        this.month = month;
        this.planId = planId;
        this.revenue = revenue;
        this.transactionCount = transactionCount;
    }
}
