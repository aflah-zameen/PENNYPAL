package com.application.pennypal.domain.coin;

import com.application.pennypal.domain.shared.exception.DomainErrorCode;
import com.application.pennypal.domain.shared.exception.NegativeAmountNotAllowedExceptionDomain;
import com.application.pennypal.domain.shared.exception.base.DomainBusinessException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCoinAccount {
    private final String userId;
    private BigDecimal balance;
    private BigDecimal totalEarned;
    private  final LocalDateTime lastUpdated;

    public static UserCoinAccount create(
            String userId,
            BigDecimal balance,
            BigDecimal totalEarned
    ){
        return new UserCoinAccount(
              userId,
              balance,
              totalEarned,
              null
        );
    }

    public static UserCoinAccount reconstruct(
            String userId,
            BigDecimal balance,
            BigDecimal totalEarned,
            LocalDateTime lastUpdated
    ){
        return new UserCoinAccount(
                userId,
                balance,
                totalEarned,
                lastUpdated
        );
    }
    public void addCoins(BigDecimal coins) {
        if(coins.compareTo(BigDecimal.ZERO) < 0){
            throw new NegativeAmountNotAllowedExceptionDomain();
        }

        this.balance = this.balance.add(coins);
        this.totalEarned = this.totalEarned.add(coins);
    }

    public void subtractCoins(BigDecimal coins) {
        if (this.balance.compareTo(coins) < 0) {
            throw new DomainBusinessException("Insufficient coins", DomainErrorCode.INSUFFICIENT_BALANCE);
        }
        this.balance = this.balance.subtract(coins);
    }

}
