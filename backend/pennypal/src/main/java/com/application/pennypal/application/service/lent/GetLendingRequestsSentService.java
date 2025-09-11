package com.application.pennypal.application.service.lent;

import com.application.pennypal.application.dto.output.lend.LendingRequestOutputModel;
import com.application.pennypal.application.mappers.lent.LentApplicationMapper;
import com.application.pennypal.application.port.in.lent.GetLendingRequestsSent;
import com.application.pennypal.application.port.out.repository.LendingRequestRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetLendingRequestsSentService implements GetLendingRequestsSent {
    private final LendingRequestRepositoryPort lendingRequestRepositoryPort;
    private final LentApplicationMapper lentApplicationMapper;
    @Override
    public List<LendingRequestOutputModel> execute(String userId) {
        return lendingRequestRepositoryPort.getAllLendingRequestsSent(userId).stream()
                .map(lentApplicationMapper::toOutput)
                .toList();
    }
}
