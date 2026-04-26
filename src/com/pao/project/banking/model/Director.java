package com.pao.project.banking.model;

import java.time.LocalDate;

public class Director extends Angajat {

    private String departamentCondus;
    private double bonusAnual;

    public Director(String nume, String prenume, String cnp, LocalDate dataNasterii, String telefon, String email, Adresa adresa, String functie, double salariu, String departament, String departamentCondus, double bonusAnual) {
        super(nume, prenume, cnp, dataNasterii, telefon, email, adresa, functie, salariu, departament);
        this.departamentCondus = departamentCondus;
        this.bonusAnual = bonusAnual;
    }

    @Override
    public String getRol() {
        return "Director";
    }

    public String getDepartamentCondus() {
        return departamentCondus;
    }

    public void setDepartamentCondus(String departamentCondus) {
        this.departamentCondus = departamentCondus;
    }

    public double getBonusAnual() {
        return bonusAnual;
    }

    public void setBonusAnual(double bonusAnual) {
        this.bonusAnual = bonusAnual;
    }

    @Override
    public String toString() {
        return super.toString() +
               " | Dept. Condus: " + departamentCondus +
               " | Bonus Anual: " + String.format("%.2f", bonusAnual) + " RON";
    }
}
