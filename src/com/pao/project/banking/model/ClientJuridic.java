package com.pao.project.banking.model;

public class ClientJuridic extends Client {

    private String cui;
    private String numeFirma;
    private String domeniuActivitate;

    public ClientJuridic(String numeFirma, String reprezentantNume, String reprezentantPrenume, String telefon, String email, Adresa adresa, String cui, String domeniuActivitate) {
        super(reprezentantNume, reprezentantPrenume, telefon, email, adresa);
        this.numeFirma = numeFirma;
        this.cui = cui;
        this.domeniuActivitate = domeniuActivitate;
    }

    @Override
    public String getRol() {
        return "Client Juridic";
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getNumeFirma() {
        return numeFirma;
    }

    public void setNumeFirma(String numeFirma) {
        this.numeFirma = numeFirma;
    }

    public String getDomeniuActivitate() {
        return domeniuActivitate;
    }

    public void setDomeniuActivitate(String d) {
        this.domeniuActivitate = d;
    }

    @Override
    public String toString() {
        return "[" + getRol() + "] Firma: " + numeFirma + 
               " | CUI: " + cui + 
               " | Domeniu: " + domeniuActivitate + 
               " | Reprezentant: " + getNumeComplet() + 
               " | ID Client: " + getIdClient();
    }
}
