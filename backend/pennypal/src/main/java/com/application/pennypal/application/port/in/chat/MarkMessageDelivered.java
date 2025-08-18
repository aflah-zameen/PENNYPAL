package com.application.pennypal.application.port.in.chat;

import java.util.List;

public interface MarkMessageDelivered {
    void handle(List<String> messageIds);
}
