package com.pao.project.banking.model;

import java.time.LocalDateTime;

public final class Tranzactie implements Comparable<Tranzactie> {

    public enum TipTranzactie {
        DEPUNERE, RETRAGERE, TRANSFER_TRIMIS, TRANSFER_PRIMIT
    }

    private static int contorTranzactii = 1;

    private final String idTranzactie;
    private final String ibanSursa;
    private final String ibanDestinatie;
    private final double suma;
    private final TipTranzactie tip;
    private final LocalDateTime dataOra;
    private final String descriere;

    {
        this.idTranzactie = "TRX-" + contorTranzactii;
        contorTranzactii++;
    }

    public Tranzactie(String ibanSursa, String ibanDestinatie, double suma, TipTranzactie tip, String descriere) {
        this.ibanSursa = ibanSursa;
        this.ibanDestinatie = ibanDestinatie;
        this.suma = suma;
        this.tip = tip;
        this.dataOra = LocalDateTime.now();
        this.descriere = descriere;
    }

    public String getIdTranzactie() {
        return idTranzactie;
    }

    public String getIbanSursa() {
        return ibanSursa;
    }

    public String getIbanDestinatie() {
        return ibanDestinatie;
    }

    public double getSuma() {
        return suma;
    }

    public TipTranzactie getTip() {
        return tip;
    }

    public LocalDateTime getDataOra() {
        return dataOra;
    }

    public String getDescriere() {
        return descriere;
    }

    @Override
    public int compareTo(Tranzactie other) {
        return other.dataOra.compareTo(this.dataOra);
    }

    @Override
    public String toString() {
        return "Tranzactie: " + idTranzactie + 
               " | Tip: " + tip + 
               " | Suma: " + suma + " RON" +
               " | De la: " + ibanSursa + 
               " | Catre: " + ibanDestinatie + 
               " | Data: " + dataOra;
    }
}