package com.pao.laboratory06.exercise3;

import java.util.ArrayList;
import java.util.List;

class PersoanaJuridica extends Persoana implements PlataOnlineSMS {
    private List<String> smsTrimise = new ArrayList<>();
    private double soldRegie = 10000.0;

    public PersoanaJuridica(String nume, String telefon) {
        super(nume, "", telefon);
    }

    @Override
    public boolean trimiteSMS(String mesaj) {
        if (this.telefon == null || this.telefon.isEmpty() || mesaj == null || mesaj.isEmpty()) {
            return false;
        }
        smsTrimise.add(mesaj);
        return true;
    }

    public List<String> getSmsTrimise() { 
        return smsTrimise;
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isEmpty() || parola == null || parola.isEmpty()) {
            throw new IllegalArgumentException("Credentiale invalide!");
        }
        System.out.println("Persoana juridica " + nume + " autentificata cu succes.");
    }

    @Override
    public double consultareSold() {
        return soldRegie;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma <= 0 || suma > soldRegie) {
            return false;
        }
        soldRegie -= suma;
        return true;
    }
}
