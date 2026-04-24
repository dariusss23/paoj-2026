package com.pao.project.banking.exception;

public class CardBlocatException extends Exception {
    public CardBlocatException(String numarCard) {
        super("Cardul cu numarul '" + numarCard + "' este blocat si nu poate efectua operatiuni.");
    }
}
