package com.application.pennypal.application.service.user;

import com.application.pennypal.application.dto.UserUpdateApplicationDTO;
import com.application.pennypal.application.port.UpdateUserPort;
import com.application.pennypal.application.usecases.user.UpdateUser;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.user.valueObject.UserDomainDTO;

public class UpdateUserService implements UpdateUser {
    private final UpdateUserPort updateUserPort;
    public UpdateUserService(UpdateUserPort updateUserPort){
        this.updateUserPort = updateUserPort;
    }

    @Override
    public UserDomainDTO update(UserUpdateApplicationDTO user, String token) {
        User updatedUser = this.updateUserPort.update(user,token);
        return new UserDomainDTO(updatedUser.getId(),updatedUser.getName(),updatedUser.getEmail(),
                updatedUser.getRoles(),updatedUser.getPhone(),updatedUser.isActive(),
                updatedUser.isVerified(),updatedUser.getCreatedAt(),updatedUser.getUpdatedAt(),
                updatedUser.getProfileURL());
    }
}
