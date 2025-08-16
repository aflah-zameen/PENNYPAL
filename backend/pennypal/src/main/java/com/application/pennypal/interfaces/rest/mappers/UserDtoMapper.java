package com.application.pennypal.interfaces.rest.mappers;

import com.application.pennypal.application.dto.input.user.RegisterInputModel;
import com.application.pennypal.interfaces.rest.dtos.auth.RegisterRequestDTO;

public class UserDtoMapper {
    public static RegisterInputModel toInput(RegisterRequestDTO requestDTO){
        return new RegisterInputModel(
                requestDTO.getName(),
                requestDTO.getEmail(),
                requestDTO.getPassword(),
                requestDTO.getPhone(),
                requestDTO.getProfilePicture()
        );
    }
}
