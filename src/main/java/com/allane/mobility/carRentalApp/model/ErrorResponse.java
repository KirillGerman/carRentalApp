package com.allane.mobility.carRentalApp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"message", "time" })
public class ErrorResponse {

    private final String message;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime time;

    public ErrorResponse(String message) {
        this.time = LocalDateTime.now();
        this.message = message;
    }
}