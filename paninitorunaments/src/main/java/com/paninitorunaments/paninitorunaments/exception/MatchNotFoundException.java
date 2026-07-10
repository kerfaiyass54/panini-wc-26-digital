package com.paninitorunaments.paninitorunaments.exception;

public class MatchNotFoundException
        extends RuntimeException {

    public MatchNotFoundException(Long id) {
        super("Match with id " + id + " not found");
    }
}