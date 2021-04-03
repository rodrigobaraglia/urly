package com.rodrigobaraglia.urly.model.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDTO {
    public final HttpStatus code;
    public final String message;
}
