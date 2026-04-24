package com.pao.project.banking.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Client extends Persoana {

    private static int contor = 1000;

    private int idClient;
    private List<Cont> conturi;

    {
        idClient = ++contor;
        conturi = new ArrayList<>();
        System.out.println("Client nou creat cu idClient = " + idClient);
    }

    public Client(String nume, String prenume, String telefon, String email, Adresa adresa) {
        super(nume, prenume, telefon, email, adresa);
    }

    public int getIdClient() { 
        return idClient;
    }

    public List<Cont> getConturi() {
        return conturi;
    }

    public void adaugaCont(Cont cont) {
        conturi.add(cont);
    }

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
    public String toString() {
        return super.toString() + " | ID Client: " + idClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Client))
            return false;
        Client other = (Client) o;
        return this.idClient == other.idClient;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idClient);
    }
}
