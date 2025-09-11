package com.application.pennypal.interfaces.rest.dtos.coin;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RedemptionRequestDTO(

        @NotNull(message = "Coin amount is required")
        @DecimalMin(value = "100.00", inclusive = true, message = "Minimum redeemable coin amount is 100")
        @Digits(integer = 10, fraction = 2, message = "Coin amount must be a valid decimal with up to 2 decimal places")
        BigDecimal coinAmount,

        @NotNull(message = "Real money amount is required")
        @DecimalMin(value = "0.01", inclusive = true, message = "Real money amount must be greater than zero")
        @Digits(integer = 10, fraction = 2, message = "Real money amount must be a valid decimal with up to 2 decimal places")
        BigDecimal realMoneyAmount

){}
