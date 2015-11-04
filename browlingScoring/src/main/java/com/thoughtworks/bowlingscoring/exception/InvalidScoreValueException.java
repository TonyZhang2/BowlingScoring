package com.thoughtworks.bowlingscoring.exception;

public class InvalidScoreValueException extends RuntimeException {
    public InvalidScoreValueException(String msg) {
        super(msg);
    }
}
