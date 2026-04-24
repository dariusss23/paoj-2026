package com.pao.project.banking.exception;

public class ClientNegasitException extends Exception {
    public ClientNegasitException(String criteriu) {
        super("Clientul nu a fost gasit dupa criteriul: " + criteriu);
    }
}
