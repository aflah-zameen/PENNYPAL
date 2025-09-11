package com.application.pennypal.infrastructure.adapter.out.lent;

import com.application.pennypal.application.dto.input.lend.LoanCaseInputModel;
import com.application.pennypal.application.dto.output.lend.LoanCaseOutputModel;
import com.application.pennypal.application.dto.output.lend.LoanOutputModel;
import com.application.pennypal.application.port.in.lent.FileLoanCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileLoanCaseInfraService {
    private final FileLoanCase fileLoanCase;

    @Transactional
    public LoanOutputModel execute(String userId, LoanCaseInputModel inputModel){
        return fileLoanCase.execute(userId,inputModel);
    }
}
