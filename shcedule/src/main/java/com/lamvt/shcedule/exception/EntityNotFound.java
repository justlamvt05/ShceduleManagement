package com.lamvt.shcedule.exception;

import lombok.Getter;

@Getter
public class EntityNotFound extends RuntimeException {
    public EntityNotFound(String msg) {
        super(msg);
    }
}
