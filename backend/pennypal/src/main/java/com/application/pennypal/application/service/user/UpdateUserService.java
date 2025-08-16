package com.application.pennypal.application.service.user;

import com.application.pennypal.application.dto.output.user.UserUpdateApplicationOutput;
import com.application.pennypal.application.port.out.service.UpdateUserPort;
import com.application.pennypal.application.port.in.user.UpdateUser;
import com.application.pennypal.domain.user.entity.User;
import com.application.pennypal.domain.valueObject.UserDomainDTO;

public class UpdateUserService implements UpdateUser {
    private final UpdateUserPort updateUserPort;
    public UpdateUserService(UpdateUserPort updateUserPort){
        this.updateUserPort = updateUserPort;
    }

    @Override
    public UserDomainDTO update(UserUpdateApplicationOutput user, String token) {
        User updatedUser = this.updateUserPort.update(user,token);
        return new UserDomainDTO(updatedUser.getUserId(),updatedUser.getName(),updatedUser.getEmail(),
                updatedUser.getRoles(),updatedUser.getPhone(),updatedUser.isActive(),
                updatedUser.isVerified(),updatedUser.getCreatedAt(),
                updatedUser.getProfileURL().isPresent()? updatedUser.getProfileURL().get() : null);
    }
}
