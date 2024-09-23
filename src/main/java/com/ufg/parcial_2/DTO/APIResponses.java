package com.ufg.parcial_2.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIResponses {
    private int status;
    private String message;
    private Object data;

    public APIResponses(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}