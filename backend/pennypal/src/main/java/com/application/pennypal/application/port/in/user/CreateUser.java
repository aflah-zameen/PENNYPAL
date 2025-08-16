package com.application.pennypal.application.port.in.user;

import com.application.pennypal.application.dto.input.user.RegisterInputModel;
import com.application.pennypal.application.dto.output.user.RegisterOutputModel;
import com.application.pennypal.application.dto.output.user.UserOutputModel;

public interface CreateUser {
    RegisterOutputModel execute (RegisterInputModel registerInputModel);
}
