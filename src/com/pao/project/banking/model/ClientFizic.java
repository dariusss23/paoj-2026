package com.pao.project.banking.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientFizic extends Persoana implements Client {

    private static int contor = 1000;

    private int idClient;
    private List<Cont> conturi;
    private boolean isStudent;

    {
        idClient = ++contor;
        conturi = new ArrayList<>();
        System.out.println("Client Fizic nou creat cu idClient = " + idClient);
    }

    public ClientFizic(String nume, String prenume, String cnp, LocalDate dataNasterii, String telefon, String email, Adresa adresa, boolean isStudent) {
        super(nume, prenume, cnp, dataNasterii, telefon, email, adresa);
        this.isStudent = isStudent;
    }

    // CONSTRUCTOR 2: Pentru cand incarc un client EXISTENT din baza de date
    public ClientFizic(int idClient, String nume, String prenume, String cnp, LocalDate dataNasterii, String telefon, String email, Adresa adresa, boolean isStudent) {
        super(nume, prenume, cnp, dataNasterii, telefon, email, adresa);
        this.idClient = idClient;
        this.isStudent = isStudent;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
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
    public String getRol() {
        return "Client Fizic";
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    @Override
    public String toString() {
        return super.toString() +
               " | CNP: " + getCnp() +
               " | Nastere: " + getDataNasterii() +
               " | ID Client: " + idClient +
               " | Student: " + (isStudent ? "Da" : "Nu");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) 
            return true;
        if (!(o instanceof ClientFizic))
            return false;
        return this.idClient == ((ClientFizic) o).idClient;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idClient);
    }
}
