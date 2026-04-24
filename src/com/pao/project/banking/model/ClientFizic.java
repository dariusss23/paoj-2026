package com.pao.project.banking.model;

import java.time.LocalDate;

public class ClientFizic extends Client {

    private String cnp;
    private LocalDate dataNasterii;

    public ClientFizic(String nume, String prenume, String telefon, String email, Adresa adresa, String cnp, LocalDate dataNasterii) {
        super(nume, prenume, telefon, email, adresa);
        this.cnp = cnp;
        this.dataNasterii = dataNasterii;
    }

    @Override
    public String getRol() {
        return "Client Fizic";
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

    @Override
    public String toString() {
        return super.toString() + " | CNP: " + cnp + " | Nastere: " + dataNasterii;
    }
}
