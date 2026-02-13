package com.lamvt.shcedule.exception;

import lombok.Getter;

@Getter
public class InvalidInputException extends IllegalArgumentException {
    public InvalidInputException(String s) {
        super(s);
    }
}
