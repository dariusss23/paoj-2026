package com.pao.project.banking.model;

public class Card {

    public enum TipCard {
        DEBIT, CREDIT
    }

    public enum StareCard {
        ACTIV, BLOCAT, EXPIRAT
    }

    private final String numarCard;
    private final String ibanCont;
    private TipCard tipCard;
    private StareCard stare;
    private final String dataExpirare;
    private final String cvv;

    public Card(String numarCard, String ibanCont, TipCard tipCard, String dataExpirare, String cvv) {
        this.numarCard = numarCard;
        this.ibanCont = ibanCont;
        this.tipCard = tipCard;
        this.stare = StareCard.ACTIV;
        this.dataExpirare = dataExpirare;
        this.cvv = cvv;
    }

    public String getNumarCard() { 
        return numarCard; 
    }

    public String getIbanCont() { 
        return ibanCont;
    }

    public TipCard getTipCard() {
        return tipCard;
    }

    public StareCard getStare() {
        return stare;
    }

    public String getCVV() {
        return cvv;
    }

    public void setStare(StareCard stare) {
        this.stare = stare;
    }

    public String getDataExpirare() {
        return dataExpirare; 
    }

    public boolean verificaCvv(String cvvIntroodus) {
        return this.cvv.equals(cvvIntroodus);
    }

    @Override
    public String toString() {
        String mascat = "**** **** **** " + numarCard.substring(numarCard.length() - 4);
        return String.format("Card [%s] %s | Exp: %s | Stare: %s", tipCard, mascat, dataExpirare, stare);
    }
}
