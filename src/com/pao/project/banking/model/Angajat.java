package com.pao.project.banking.model;


public class Angajat extends Persoana {

    private static int contor = 100;

    private int idAngajat;
    private String functie;
    private double salariu;
    private String departament;

    {
        idAngajat = ++contor;
        System.out.println("Angajat nou creat cu idAngajat = " + idAngajat);
    }

    public Angajat(String nume, String prenume, String telefon, String email, Adresa adresa, String functie, double salariu, String departament) {
        super(nume, prenume, telefon, email, adresa);
        this.functie = functie;
        this.salariu = salariu;
        this.departament = departament;
    }

    @Override
    public String getRol() {
        return "Angajat";
    }

    public int getIdAngajat() {
        return idAngajat;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public double getSalariu() {
        return salariu;
    }

    public void setSalariu(double salariu) {
        this.salariu = salariu;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    @Override
    public String toString() {
        return super.toString() +
               " | ID Angajat: " + idAngajat +
               " | Functie: " + functie +
               " | Departament: " + departament +
               " | Salariu: " + String.format("%.2f", salariu) + " RON";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Angajat)) return false;
        Angajat other = (Angajat) o;
        return this.idAngajat == other.idAngajat;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idAngajat);
    }
}
