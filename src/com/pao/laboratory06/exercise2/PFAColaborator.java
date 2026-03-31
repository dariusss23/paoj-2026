package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends PersoanaFizica {
    private double cheltuieliLunare;

    private static final double SALARIU_MINIM_BRUT_LUNAR = 4050.00;
    private static final double SALARIU_MINIM_BRUT_ANUAL = SALARIU_MINIM_BRUT_LUNAR * 12;

    public PFAColaborator() {
        this.tip = TipColaborator.PFA;
    }

    public PFAColaborator(String nume, String prenume, double venitLunar, double cheltuieliLunare) {
        super(nume, prenume, venitLunar, TipColaborator.PFA);
        this.cheltuieliLunare = cheltuieliLunare;
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNet = (venitBrutLunar - cheltuieliLunare) * 12;

        double impozit = 0.10 * venitNet;

        double cass;
        double prag6 = 6 * SALARIU_MINIM_BRUT_ANUAL;
        double prag72 = 72 * SALARIU_MINIM_BRUT_ANUAL;
        if (venitNet < prag6) {
            cass = 0.10 * prag6;
        } 
        else if (venitNet <= prag72) {
            cass = 0.10 * venitNet;
        } 
        else {
            cass = 0.10 * prag72;
        }

        double prag12 = 12 * SALARIU_MINIM_BRUT_ANUAL;
        double prag24 = 24 * SALARIU_MINIM_BRUT_ANUAL;
        double cas;
        if (venitNet < prag12) {
            cas = 0;
        } 
        else if (venitNet <= prag24) {
            cas = 0.25 * prag12;
        } else {
            cas = 0.25 * prag24;
        }

        return venitNet - impozit - cass - cas;
    }

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        this.cheltuieliLunare = in.nextDouble();
    }

    @Override
    public void afiseaza() {
        System.out.printf("PFA: %s %s, venit net anual: %.2f lei%n", nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return "PFA";
    }
}