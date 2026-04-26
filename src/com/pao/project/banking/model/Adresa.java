package com.pao.project.banking.model;

public final class Adresa {
    private final String strada;
    private final String oras;
    private final String judet;
    private final String tara;

    public Adresa(String strada, String oras, String judet, String tara) {
        this.strada = strada;
        this.oras = oras;
        this.judet = judet;
        this.tara = tara;
    }

    public String getStrada() {
        return strada;
    }

    public String getOras() {
        return oras;
    }

    public String getJudet() {
        return judet;
    }

    public String getTara() {
        return tara;
    }

    @Override
    public String toString() {
        return strada + ", " + oras + ", " + judet + ", " + tara;
    }
}