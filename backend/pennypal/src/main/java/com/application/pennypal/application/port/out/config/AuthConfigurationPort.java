package com.application.pennypal.application.port.out.config;

import java.time.Duration;

public interface AuthConfigurationPort {
    Duration getOtpExpiration();
    Duration getVerificationEmailExpiration();
}
