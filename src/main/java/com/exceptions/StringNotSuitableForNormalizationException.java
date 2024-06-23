package com.exceptions;

public class StringNotSuitableForNormalizationException extends RuntimeException {

    public StringNotSuitableForNormalizationException() {
        super("Job title is not suitable for Normalization");
    }
}
