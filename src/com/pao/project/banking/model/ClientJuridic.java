package com.pao.project.banking.model;

import java.util.ArrayList;
import java.util.List;

public class ClientJuridic implements Client {

    public enum TipFirma {
        SOCIETATE_COMERCIALA, SRL, SA, PFA, ONG
    }

    private static int contor = 5000;

    private int idClient;
    private List<Cont> conturi;
    private String numeFirma;
    private TipFirma tipFirma;
    private String cui;
    private String domeniuActivitate;
    private String reprezentantNume;
    private String reprezentantPrenume;
    private String telefon;
    private String email;
    private Adresa adresa;

    {
        idClient = ++contor;
        conturi = new ArrayList<>();
        System.out.println("Client Juridic nou creat cu idClient = " + idClient);
    }

    public ClientJuridic(String numeFirma, String cui, TipFirma tipFirma, String domeniuActivitate, String reprezentantNume, String reprezentantPrenume, String telefon, String email, Adresa adresa) {
        this.numeFirma = numeFirma;
        this.cui = cui;
        this.tipFirma = tipFirma;
        this.domeniuActivitate = domeniuActivitate;
        this.reprezentantNume = reprezentantNume;
        this.reprezentantPrenume = reprezentantPrenume;
        this.telefon = telefon;
        this.email = email;
        this.adresa = adresa;
    }

    @Override
    public int getIdClient() {
        return idClient;
    }

    @Override
    public List<Cont> getConturi() {
        return conturi;
    }

    @Override
    public void adaugaCont(Cont cont) {
        conturi.add(cont);
    }

    @Override
    public void stergeContDupaIban(String iban) {
        Cont contDeSters = null;
        for (Cont c : conturi) {
            if (c.getIban().equalsIgnoreCase(iban)) {
                contDeSters = c;
                break;
            }
        }
        if (contDeSters != null) {
            conturi.remove(contDeSters);
        }
    }

    @Override
    public String getNumeComplet() {
        return numeFirma;
    }

    @Override
    public String getRol() {
        return "Client Juridic";
    }

    public String getNumeFirma() {
        return numeFirma;
    }

    public void setNumeFirma(String numeFirma) {
        this.numeFirma = numeFirma;
    }

    public TipFirma getTipFirma() {
        return tipFirma;
    }

    public void setTipFirma(TipFirma tipFirma) {
        this.tipFirma = tipFirma;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getDomeniuActivitate() {
        return domeniuActivitate;
    }

    public void setDomeniuActivitate(String domeniuActivitate) {
        this.domeniuActivitate = domeniuActivitate;
    }

    public String getReprezentantNume() {
        return reprezentantNume;
    }

    public void setReprezentantNume(String reprezentantNume) {
        this.reprezentantNume = reprezentantNume;
    }

    public String getReprezentantPrenume() {
        return reprezentantPrenume;
    }

    public void setReprezentantPrenume(String reprezentantPrenume) {
        this.reprezentantPrenume = reprezentantPrenume;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    @Override
    public String toString() {
        return "[" + getRol() + "] " + numeFirma + " (" + tipFirma + ")" +
                " | CUI: " + cui +
                " | Domeniu: " + domeniuActivitate +
                " | Reprezentant: " + reprezentantNume + " " + reprezentantPrenume +
                " | Tel: " + telefon + " | Email: " + email +
                " | ID Client: " + idClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ClientJuridic))
            return false;
        return this.idClient == ((ClientJuridic) o).idClient;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idClient);
    }
}