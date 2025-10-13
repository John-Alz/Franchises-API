package com.api.franchises.infrastructure.entrypoints.util;

import org.springframework.web.reactive.function.server.ServerRequest;

import static com.api.franchises.infrastructure.entrypoints.util.Constants.X_MESSAGE_ID;

public class MessageId {

    private MessageId() {}

    public static String getMessageId(ServerRequest serverRequest) {
        return serverRequest.headers().firstHeader(X_MESSAGE_ID);
    }

}
