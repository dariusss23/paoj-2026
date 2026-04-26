package com.pao.project.banking.model;

import java.time.LocalDate;

public abstract class Persoana {
    private String nume;
    private String prenume;
    private String cnp;
    private LocalDate dataNasterii;
    private String telefon;
    private String email;
    private Adresa adresa;

    public Persoana(String nume, String prenume, String cnp, LocalDate dataNasterii, String telefon, String email, Adresa adresa) {
        this.nume = nume;
        this.prenume = prenume;
        this.cnp = cnp;
        this.dataNasterii = dataNasterii;
        this.telefon = telefon;
        this.email = email;
        this.adresa = adresa;
    }

    public abstract String getRol();

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public LocalDate getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(LocalDate dataNasterii) {
        this.dataNasterii = dataNasterii;
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

    public String getNumeComplet() {
        return nume + " " + prenume;
    }

    @Override
    public String toString() {
        return "[" + getRol() + "] " + getNumeComplet() +
               " | Tel: " + telefon +
               " | Email: " + email;
    }
}