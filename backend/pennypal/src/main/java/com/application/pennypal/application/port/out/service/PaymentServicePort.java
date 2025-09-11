package com.application.pennypal.application.port.out.service;

import com.application.pennypal.application.dto.output.payment.PaymentResultOutputModel;

public interface PaymentServicePort {

    PaymentResultOutputModel chargeUser(String userId,String planId);
}
