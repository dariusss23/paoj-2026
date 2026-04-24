package com.pao.project.banking.exception;

public class SoldInsuficientException extends Exception {
    private final double soldDisponibil;
    private final double sumaIncercata;

    public SoldInsuficientException(double soldDisponibil, double sumaIncercata) {
        super(String.format("Sold insuficient! Disponibil: %.2f RON, Solicitat: %.2f RON", soldDisponibil, sumaIncercata));
        this.soldDisponibil = soldDisponibil;
        this.sumaIncercata = sumaIncercata;
    }

    public double getSoldDisponibil() {
        return soldDisponibil;
    }

    public double getSumaIncercata() {
        return sumaIncercata;
    }
}
