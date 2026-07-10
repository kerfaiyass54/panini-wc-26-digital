package com.paninitorunaments.paninitorunaments.exception;

public class TournamentNotFoundException extends RuntimeException {

    public TournamentNotFoundException(Long id) {
        super("Tournament with id " + id + " not found");
    }
}