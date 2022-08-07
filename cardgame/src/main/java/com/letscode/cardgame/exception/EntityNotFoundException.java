package com.letscode.cardgame.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String entity) {
        super("Não foi possível encontrar " + entity);
    }
}
