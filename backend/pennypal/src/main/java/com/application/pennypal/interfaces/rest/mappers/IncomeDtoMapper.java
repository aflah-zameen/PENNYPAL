package com.application.pennypal.interfaces.rest.mappers;

import com.application.pennypal.application.dto.input.income.IncomeInputModel;
import com.application.pennypal.application.dto.output.income.AllPendingIncomeSummaryOutput;
import com.application.pennypal.application.dto.output.income.IncomeOutputModel;
import com.application.pennypal.application.dto.output.income.IncomeSummaryOutput;
import com.application.pennypal.domain.valueObject.RecurrenceFrequency;
import com.application.pennypal.interfaces.rest.dtos.income.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class IncomeDtoMapper {
    public static IncomeInputModel toInput(AddIncomeRequestDTO addIncomeRequestDTO){
        return new IncomeInputModel(
                addIncomeRequestDTO.title().trim(),
                addIncomeRequestDTO.amount(),
                addIncomeRequestDTO.categoryId(),
                addIncomeRequestDTO.incomeDate() != null ? LocalDate.parse(addIncomeRequestDTO.incomeDate().trim()) : null,
                addIncomeRequestDTO.description().trim(),
                addIncomeRequestDTO.isRecurring(),
                addIncomeRequestDTO.startDate() != null && addIncomeRequestDTO.isRecurring() ? LocalDate.parse(addIncomeRequestDTO.startDate()) : null,
                addIncomeRequestDTO.endDate() != null && addIncomeRequestDTO.isRecurring() ? LocalDate.parse(addIncomeRequestDTO.endDate()) : null,
                addIncomeRequestDTO.frequency() != null && addIncomeRequestDTO.isRecurring() ? RecurrenceFrequency.valueOf(addIncomeRequestDTO.frequency().trim().toUpperCase()) : null,
                addIncomeRequestDTO.isRecurring()
        );
    }

    public static IncomeResponseDTO toResponse(IncomeOutputModel incomeOutputModel){
        return new IncomeResponseDTO(
                "incomeOutputModel.id()",
                incomeOutputModel.amount(),
                incomeOutputModel.title(),
                CategoryDtoMapper.toResponse(incomeOutputModel.category()),
                incomeOutputModel.incomeDate(),
                incomeOutputModel.status().getValue(),
                incomeOutputModel.description(),
                incomeOutputModel.isRecurring(),
                incomeOutputModel.frequency() != null ? incomeOutputModel.frequency().getValue() : null,
                incomeOutputModel.startDate(),
                incomeOutputModel.endDate(),
                incomeOutputModel.updatedAt(),
                incomeOutputModel.createdAt(),
                incomeOutputModel.recurrenceActive(),
                incomeOutputModel.deleted()
        );
    }

    public static IncomeSummaryResponseDTO toResponse(IncomeSummaryOutput incomeSummaryOutput){
        return new IncomeSummaryResponseDTO(
              new TotalIncomeSummaryDTO(
                      incomeSummaryOutput.totalIncomeSummaryOutput().totalIncomes(),
                      incomeSummaryOutput.totalIncomeSummaryOutput().progressValue()
              ),
                new PendingIncomeSummaryDTO(
                        incomeSummaryOutput.pendingIncomeSummaryOutput().totalAmount(),
                        incomeSummaryOutput.pendingIncomeSummaryOutput().pendingIncomes()
                ),
                new ActiveRecurringIncomeDTO(
                        incomeSummaryOutput.activeRecurringIncomeOutput().count()
                )
        );
    }

    public static AllPendingIncomeSummaryDTO toResponse(AllPendingIncomeSummaryOutput allPendingIncomeSummaryOutput)
    {
        return new AllPendingIncomeSummaryDTO(
                allPendingIncomeSummaryOutput.pendingIncomeOutputList().stream()
                        .map(pendingIncomeOutput ->
                                new PendingIncomeResponseDTO(
                                        pendingIncomeOutput.incomeId(),
                                        pendingIncomeOutput.title(),
                                        pendingIncomeOutput.amount(),
                                        pendingIncomeOutput.incomeDate(),
                                        CategoryDtoMapper.toResponse(pendingIncomeOutput.category())
                                )).toList(),
                allPendingIncomeSummaryOutput.totalCount(),
                allPendingIncomeSummaryOutput.totalAmount()
        );
    }
}
