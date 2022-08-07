package com.letscode.cardgame.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(){
        super("Player não autorizado para jogar a partida escolhida");
    }
}
