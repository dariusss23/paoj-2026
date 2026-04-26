package com.pao.project.banking.model;

import java.util.List;

public interface Client {
    int getIdClient();
    List<Cont> getConturi();
    void adaugaCont(Cont cont);
    void stergeContDupaIban(String iban);
    String getNumeComplet();
    String getRol();
}