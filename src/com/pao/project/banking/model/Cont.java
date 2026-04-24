package com.pao.project.banking.model;

import java.util.ArrayList;
import java.util.List;

public class Cont {

    public enum TipCont {
        CURENT, ECONOMII
    }

    private final String iban;
    private double sold;
    private final TipCont tipCont;
    private final String moneda;
    private final int idProprietar;
    private List<Card> carduri;
    private List<Tranzactie> tranzactii;

    public Cont(String iban, double soldInitial, TipCont tipCont, String moneda, int idProprietar) {
        this.iban = iban;
        this.sold = soldInitial;
        this.tipCont = tipCont;
        this.moneda = moneda;
        this.idProprietar = idProprietar;
        this.carduri = new ArrayList<>();
        this.tranzactii = new ArrayList<>();
    }

    public String getIban() { 
        return iban;
    }

    public double getSold() { 
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    public TipCont getTipCont() {
        return tipCont;
    }

    public String getMoneda() {
        return moneda;
    }

    public int getIdProprietar() {
        return idProprietar;
    }

    public List<Card> getCarduri() {
        return carduri;
    }

    public void adaugaCard(Card card) { 
        carduri.add(card);
    }

    public List<Tranzactie> getTranzactii() {
        return tranzactii;
    }

    public void adaugaTranzactie(Tranzactie t) {
        tranzactii.add(t);
    }

    @Override
    public String toString() {
        return String.format("Cont [%s] | IBAN: %s | Sold: %.2f %s | Carduri: %d", tipCont, iban, sold, moneda, carduri.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cont)) return false;
        return this.iban.equals(((Cont) o).iban);
    }

    @Override
    public int hashCode() {
        return iban.hashCode();
    }
}
