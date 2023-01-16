package com.agleveratto.superhero.application.exceptions;

public class NotFoundException extends RuntimeException {

    private static final String NOT_FOUND_EXCEPTION = "Superhero not found";

    public NotFoundException() {
        super(NOT_FOUND_EXCEPTION);
    }

}
