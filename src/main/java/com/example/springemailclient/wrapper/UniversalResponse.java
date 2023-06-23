package com.example.springemailclient.wrapper;

import lombok.Builder;

@Builder
public record UniversalResponse(int status, String message, Object data) {
}
