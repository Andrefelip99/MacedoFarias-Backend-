package com.example.confeitariaMacedoFarias.services.exepitions;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String msg) {
        super(msg);
    }

}