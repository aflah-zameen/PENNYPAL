package com.application.pennypal.application.service.user;

import com.application.pennypal.application.dto.output.user.ContactOutputModel;
import com.application.pennypal.application.dto.output.user.TransferTransaction;
import com.application.pennypal.application.port.in.user.GetContacts;
import com.application.pennypal.application.port.out.repository.TransactionRepositoryPort;
import com.application.pennypal.application.port.out.repository.UserRepositoryPort;
import com.application.pennypal.domain.transaction.entity.Transaction;
import com.application.pennypal.domain.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetContactsService implements GetContacts {
    private final UserRepositoryPort userRepositoryPort;
    private final TransactionRepositoryPort transactionRepositoryPort;
    @Override
    public List<ContactOutputModel> execute(String userId) {
        List<User> contacts = userRepositoryPort.getAllContacts(userId);
        List<Transaction> transactions = transactionRepositoryPort.findUserInvolvedTransactions(userId);

        /// Group transactions by userId
        Map<String, List<Transaction>> transactionsByUser = transactions.stream()
                .filter(t -> t.getTransferToUserId().isPresent() && t.getTransferFromUserId().isPresent())
                .collect(Collectors.groupingBy(t -> userId.equals(t.getTransferFromUserId().get())
                            ? t.getTransferToUserId().get()
                            : t.getTransferFromUserId().get()
                ));

        return contacts.stream()
                .map(contact -> new ContactOutputModel(
                            contact.getUserId(),
                            contact.getName(),
                            contact.getEmail(),
                            contact.getProfileURL().isPresent() ? contact.getProfileURL().get() : null,
                            false,
                            transactionsByUser.getOrDefault(contact.getUserId(),List.of()).stream()
                                    .map(transaction -> {
                                        return new TransferTransaction(
                                                transaction.getTransactionId(),
                                                transaction.getTransferFromUserId().orElse(""),
                                                transaction.getTransferToUserId().orElse(""),
                                                transaction.getAmount(),
                                                transaction.getDescription(),
                                                transaction.getTransactionDate(),
                                                transaction.getTransactionStatus().getValue(),
                                                "",
                                                transaction.getTransferFromUserId().get().equals(userId) ? "sent" : "received"
                                        );
                                    })
                                    .toList()
                    )
                )
                .toList();
    }
}
