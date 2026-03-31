package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends PersoanaFizica {
    private boolean bonus;

    public CIMColaborator() {
        this.tip = TipColaborator.CIM;
    }

    public CIMColaborator(String nume, String prenume, double salariuBrutLunar, boolean bonus) {
        super(nume, prenume, salariuBrutLunar, TipColaborator.CIM);
        this.bonus = bonus;
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double net = venitBrutLunar * 12 * 0.55;
        if (bonus) {
            net *= 1.10;
        }
        return net;
    }

    @Override
    public boolean areBonus() {
        return bonus;
    }

    @Override
    public void citeste(Scanner in) {
        this.nume = in.next();
        this.prenume = in.next();
        this.venitBrutLunar = in.nextDouble();
        if (in.hasNext("[A-Z]+")) {
            String bonusStr = in.next();
            this.bonus = bonusStr.equalsIgnoreCase("DA");
        } else {
            this.bonus = false;
        }
    }

    @Override
    public void afiseaza() {
        System.out.printf("CIM: %s %s, venit net anual: %.2f lei%n", nume, prenume, calculeazaVenitNetAnual());
    }

    @Override
    public String tipContract() {
        return "CIM";
    }
}