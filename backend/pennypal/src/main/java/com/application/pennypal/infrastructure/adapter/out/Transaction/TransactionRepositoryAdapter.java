package com.application.pennypal.infrastructure.adapter.out.Transaction;

import com.application.pennypal.application.dto.output.card.CardExpenseOverviewOutputModel;
import com.application.pennypal.application.dto.output.card.CardSpendingOutputModel;
import com.application.pennypal.application.dto.output.category.CategoryUserOutput;
import com.application.pennypal.application.dto.output.transaction.CategorySpendingOutputModel;
import com.application.pennypal.application.dto.output.transaction.DashIncExpChart;
import com.application.pennypal.application.dto.output.transaction.ExpenseDataChart;
import com.application.pennypal.application.dto.output.transaction.MostUsedCard;
import com.application.pennypal.application.dto.output.wallet.WalletStatsOutputModel;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.domain.catgeory.entity.Category;
import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.domain.valueObject.CategoryType;
import com.application.pennypal.domain.valueObject.PaymentMethod;
import com.application.pennypal.domain.valueObject.TransactionStatus;
import com.application.pennypal.domain.valueObject.TransactionType;
import com.application.pennypal.infrastructure.exception.base.InfrastructureException;
import com.application.pennypal.infrastructure.persistence.jpa.card.CardRepository;
import com.application.pennypal.infrastructure.persistence.jpa.category.CategoryRepository;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CardEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.CategoryEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.TransactionEntity;
import com.application.pennypal.infrastructure.persistence.jpa.entity.UserEntity;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.CategoryMapper;
import com.application.pennypal.infrastructure.persistence.jpa.mapper.TransactionJpaMapper;
import com.application.pennypal.infrastructure.persistence.jpa.transaction.SpendProjection;
import com.application.pennypal.infrastructure.persistence.jpa.transaction.TransactionRepository;
import com.application.pennypal.infrastructure.persistence.jpa.user.SpringDataUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {
    private final TransactionRepository transactionRepository;
    private final SpringDataUserRepository springDataUserRepository;
    private final CategoryRepository categoryRepository;
    private final CardRepository cardRepository;
    @Override
    public BigDecimal getTotalAmountForMonthByTransactionType(String userId,LocalDate start, LocalDate end,TransactionType transactionType) {
        return transactionRepository.getTotalAmountForMonth(userId,start,end,transactionType);
    }

    @Override
    public Transaction save(Transaction transaction) {
        UserEntity user = springDataUserRepository.findByUserId(transaction.getUserId())
                .orElseThrow(() -> new InfrastructureException("User not found","NOT_FOUND"));
        CategoryEntity category = null;
        if(transaction.getCategoryId().isPresent()){
            category = categoryRepository.findByCategoryId(transaction.getCategoryId().get())
                    .orElseThrow(() -> new InfrastructureException("Category not found","NOT_FOUND"));
        }
        CardEntity cardEntity = null;
        if(transaction.getCardId().isPresent())
            cardEntity = cardRepository.findByCardId(transaction.getCardId().get())
                    .orElseThrow(() -> new InfrastructureException("Card not found","NOT_FOUND"));
        TransactionEntity entity = TransactionJpaMapper.toEntity(transaction,user,category,cardEntity);
        Optional<TransactionEntity> transactionEntity = transactionRepository.findByTransactionId(transaction.getTransactionId());
        transactionEntity.ifPresent(value -> entity.setId(value.getId()));
        entity.updateStatus(TransactionStatus.COMPLETED);
        TransactionEntity savedTransaction = transactionRepository.save(entity);
        return TransactionJpaMapper.toDomain(savedTransaction);
    }

    @Override
    public boolean exitsDuplicateTransaction(String userId,String recurringTransactionId, LocalDate transactionDate, TransactionType transactionType ) {
        return transactionRepository.existsByUser_UserIdAndIsFromRecurringTrueAndRecurringTransactionIdAndTransactionDateAndTransactionType(userId,recurringTransactionId,transactionDate,transactionType);
    }

    @Override
    public List<Transaction> findRecentTransaction(String userId, int size,TransactionType transactionType) {
        Pageable pageable = PageRequest.of(0,size);
        return transactionRepository.findRecentIncomeTransactions(userId,transactionType,pageable).stream()
                .map(TransactionJpaMapper::toDomain).toList();
    }


    @Override
    public Optional<Transaction> getTransactionByIdAndTransactionType(String incomeId, TransactionType transactionType) {
        return transactionRepository.findByTransactionIdAndTransactionType(incomeId,transactionType)
                .map(TransactionJpaMapper::toDomain);
    }

    @Override
    public BigDecimal getTotalAmountByCardAndType(String cardId, TransactionType transactionType) {
        return transactionRepository.getTotalAmountByCardAndType(cardId,transactionType);
    }

    @Override
    public CardSpendingOutputModel findMonthlySpending(String cardId) {
        List<SpendProjection> results = transactionRepository.findMonthlySpending(cardId,TransactionType.EXPENSE);
        List<String> labels = results.stream()
                .map(p -> Month.of(p.getLabel()).getDisplayName(TextStyle.SHORT, Locale.ENGLISH))
                .toList();
        List<BigDecimal> data = results.stream()
                .map(SpendProjection::getTotal)
                .toList();
        return new CardSpendingOutputModel(labels,data);
    }

    @Override
    public CardSpendingOutputModel findWeeklySpending(String cardId) {
        List<SpendProjection> results = transactionRepository.findWeeklySpending(cardId,TransactionType.EXPENSE.getValue());
        List<String> weekLabels = List.of("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
        List<String> labels = results.stream()
                .map(p -> weekLabels.get((p.getLabel() + 5) % 7))
                .toList();
        List<BigDecimal> data = results.stream()
                .map(SpendProjection::getTotal)
                .toList();
        return new CardSpendingOutputModel(labels,data);
    }

    @Override
    public CardSpendingOutputModel findYearlySpending(String cardId) {
        List<SpendProjection> results = transactionRepository.findYearlySpending(cardId,TransactionType.EXPENSE);
        List<String> labels = results.stream()
                .map(p -> p.getLabel().toString())
                .toList();
        List<BigDecimal> data = results.stream()
                .map(SpendProjection::getTotal)
                .toList();
        return new CardSpendingOutputModel(labels,data);
    }

    @Override
    public List<CardExpenseOverviewOutputModel> calculateCardExpenseOverview(String cardId,LocalDate startDate, LocalDate endDate) {
        List<Object[]> raw= transactionRepository.getCardExpenseOverviewRaw(cardId,startDate,endDate);
        return raw.stream().map(row-> {
            String categoryId = (String) row[0];
            String name = (String) row[1];
            List<CategoryType> usageTypes = (List<CategoryType>) row[2];
            boolean isActive = (Boolean) row[3];
            boolean isDefault = (Boolean) row[4];
            int sortOrder = (Integer) row[5];
            String color = (String) row[6];
            String icon = (String) row[7];
            BigDecimal totalAmount = (BigDecimal) row[8];
            double trend = (Double) row[9];

            CategoryUserOutput categoryOutput = new CategoryUserOutput(
                    categoryId, name, usageTypes, isActive, isDefault, sortOrder, color, icon
            );

            return new CardExpenseOverviewOutputModel(categoryOutput, totalAmount, trend);
        }).toList();
    }

    @Override
    public List<Transaction> getCardTransaction(String cardId, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        return transactionRepository.findAllByCard_CardId(cardId,pageable).stream()
                .map(TransactionJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<Transaction> getAllSpendTransaction(String userId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return transactionRepository.findAllByUser_UserIdAndTransactionType(userId,TransactionType.EXPENSE,sort).stream()
                .map(TransactionJpaMapper::toDomain)
                .toList();
    }

    @Override
    public CategorySpendingOutputModel getTopCategorySpending(String userId, TransactionType transactionType) {
       List<Object[]> results =  transactionRepository.findTopCategoryByUserAndType(userId,transactionType);
       if(results != null && !results.isEmpty()){
           Object[] topCategoryResult = results.getFirst();
           CategoryEntity entity = (CategoryEntity) topCategoryResult[0];
           CategoryUserOutput categoryUserOutput =  new CategoryUserOutput(
                   entity.getCategoryId(),
                   entity.getName(),
                   entity.getUsageTypes(),
                   entity.isActive(),
                   entity.isDefault(),
                   entity.getSortOrder(),
                   entity.getColor(),
                   entity.getIcon()
           );
           BigDecimal totalSpent = (BigDecimal) results.getFirst()[1];
           Long transactionCount = (Long) results.getFirst()[2];
           return new CategorySpendingOutputModel(
                   categoryUserOutput,
                   totalSpent,
                   transactionCount
           );
       }
       return null;
    }

    @Override
    public MostUsedCard getMostUsedCard(String userId, TransactionType transactionType) {
        List<Object[]>  results =  transactionRepository.findMostUsedCardAggregate(userId,transactionType);
        if (results != null && !results.isEmpty()) {
            Object[] topCardData = results.getFirst();
            CardEntity card = (CardEntity) topCardData[0];
            BigDecimal totalAmount = (BigDecimal) topCardData[1];

            return new MostUsedCard(
                    card.getCardId(),
                    card.getName(),
                    card.getCardNumber(),
                    card.getExpiry(),
                    card.getCardType(),
                    card.getBalanceAmount(),
                    card.getGradient(),
                    card.isActive(),
                    totalAmount
                    );
        }
        return null;
    }

    @Override
    public List<Transaction> findUserInvolvedTransactions(String userId) {
        return transactionRepository.findUserInvolvedTransactions(userId).stream()
                .map(TransactionJpaMapper::toDomain)
                .toList();
    }

    @Override
    public List<DashIncExpChart> getIncomeExpenseBetween(String userId,LocalDate startDate, LocalDate endDate) {
        List<Object[]> rawData = transactionRepository.getRawDailyIncomeExpenseSummary(startDate, endDate,userId);

        return rawData.stream()
                .map(row -> {
                    LocalDate date = (LocalDate) row[0];
                    BigDecimal income = row[1] != null ? new BigDecimal(((Number) row[1]).toString()) : BigDecimal.ZERO;
                    BigDecimal expense = row[2] != null ? new BigDecimal(((Number) row[2]).toString()) : BigDecimal.ZERO;

                    return new DashIncExpChart(
                            date.toString(),
                            (BigDecimal) income,expense
                    );
                })
                .toList();
    }

    @Override
    public List<ExpenseDataChart> getExpenseBreakdownData(String userId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> rawData = transactionRepository.getRawExpenseBreakdownBetweenDatesAndUser(startDate, endDate, userId);

        BigDecimal total = rawData.stream()
                .map(row -> (BigDecimal) row[1])
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return rawData.stream()
                .map(row -> {
                    String category = (String) row[0];
                    BigDecimal amount = (BigDecimal) row[1];

                    double percentage = BigDecimal.ZERO.compareTo(total) > 0
                            ? 0.0
                            : amount.divide(total, 4, RoundingMode.HALF_UP)
                            .multiply(BigDecimal.valueOf(100))
                            .setScale(1, RoundingMode.HALF_UP)
                            .doubleValue();

                    return new ExpenseDataChart(category, amount, percentage);
                })
                .collect(Collectors.toList());

    }

    @Override
    public WalletStatsOutputModel getWalletStats(String userId) {
        Object[] result = (Object[]) transactionRepository.fetchWalletStatsByUserId(userId);

        long totalTransactions = result[0] != null ? ((Number) result[0]).longValue() : 0L;

        BigDecimal averageTransaction;
        if (result[1] instanceof BigDecimal) {
            averageTransaction = (BigDecimal) result[1];
        } else if (result[1] instanceof Double) {
            averageTransaction = BigDecimal.valueOf((Double) result[1]);
        } else {
            averageTransaction = BigDecimal.ZERO;
        }

        return new WalletStatsOutputModel(totalTransactions, averageTransaction);
    }


    @Override
    public List<Transaction> getWalletTransactions(String userId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return transactionRepository.findAllByUser_UserIdAndTransactionType(userId, TransactionType.WALLET,sort).stream()
                .map(TransactionJpaMapper::toDomain)
                .toList();
    }
}


