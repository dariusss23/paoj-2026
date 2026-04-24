package com.pao.project.banking.service;

import com.pao.project.banking.model.Angajat;

import java.util.*;

public class AngajatService {

    private static AngajatService instance;

    private final Map<Integer, Angajat> angajatiDupaId;

    private AngajatService() {
        angajatiDupaId = new LinkedHashMap<>();
    }

    public static AngajatService getInstance() {
        if (instance == null) {
            instance = new AngajatService();
        }
        return instance;
    }

    public void adaugaAngajat(Angajat angajat) {
        if (angajat == null) 
            throw new IllegalArgumentException("Angajatul nu poate fi null.");
        angajatiDupaId.put(angajat.getIdAngajat(), angajat);
        System.out.println("Angajat inregistrat: " + angajat.getNumeComplet() + " (ID: " + angajat.getIdAngajat() + ")");
    }

    public Angajat cautaDupaId(int id) {
        Angajat a = angajatiDupaId.get(id);
        if (a == null) 
            throw new NoSuchElementException("Angajatul cu ID=" + id + " nu exista.");
        return a;
    }

    public void stergeAngajat(int id) {
        Angajat a = cautaDupaId(id);
        angajatiDupaId.remove(id);
        System.out.println("Angajat eliminat: " + a.getNumeComplet());
    }

    public List<Angajat> listeazaToti() {
        return new ArrayList<>(angajatiDupaId.values());
    }

    public int numarAngajati() {
        return angajatiDupaId.size();
    }
}
