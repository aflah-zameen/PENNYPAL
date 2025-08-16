package com.application.pennypal.application.port.in.user;

import com.application.pennypal.application.dto.output.user.ContactOutputModel;

import java.util.List;

public interface GetContacts {
    List<ContactOutputModel> execute(String userId);
}
