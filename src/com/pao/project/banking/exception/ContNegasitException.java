package com.pao.project.banking.exception;

public class ContNegasitException extends Exception {
    public ContNegasitException(String iban) {
        super("Contul cu IBAN-ul '" + iban + "' nu a fost gasit in sistem.");
    }
}