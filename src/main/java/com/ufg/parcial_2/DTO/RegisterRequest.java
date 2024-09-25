package com.ufg.parcial_2.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String registerName;
    private String registerUsername;
    private String registerPassword;
    private String registerRepeatPassword;
}