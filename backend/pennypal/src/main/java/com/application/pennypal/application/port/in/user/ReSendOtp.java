package com.application.pennypal.application.port.in.user;

import java.time.LocalDateTime;

public interface ReSendOtp {
    void send(String email);
}
