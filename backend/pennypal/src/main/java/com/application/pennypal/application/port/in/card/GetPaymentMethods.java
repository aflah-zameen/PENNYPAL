package com.application.pennypal.application.port.in.card;

import com.application.pennypal.application.dto.output.card.PaymentMethodOutputModel;

import java.util.List;

public interface GetPaymentMethods {
    List<PaymentMethodOutputModel> execute(String userId);
}
