package com.pao.laboratory06.exercise2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        List<Colaborator> colaboratori = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String tip = scanner.next();
            Colaborator c;
            switch (tip) {
                case "CIM":
                    c = new CIMColaborator();
                    break;
                case "PFA":
                    c = new PFAColaborator();
                    break;
                case "SRL":
                    c = new SRLColaborator();
                    break;
                default:
                    throw new IllegalArgumentException("Tip necunoscut: " + tip);
            }
            c.citeste(scanner);
            colaboratori.add(c);
        }

        for (Colaborator c : colaboratori) {
            c.afiseaza();
        }

        for (int i = 0; i < colaboratori.size() - 1; i++) {
            for (int j = 0; j < colaboratori.size() - 1 - i; j++) {
                Colaborator a = colaboratori.get(j);
                Colaborator b = colaboratori.get(j + 1);
                if (a.calculeazaVenitNetAnual() < b.calculeazaVenitNetAnual()) {
                    colaboratori.set(j, b);
                    colaboratori.set(j + 1, a);
                }
            }
        }

        System.out.println();

        System.out.print("Colaborator cu venit net maxim: ");
        colaboratori.get(0).afiseaza();

        System.out.println();

        System.out.println("Colaboratori persoane juridice:");
        for (Colaborator c : colaboratori) {
            if (c instanceof PersoanaJuridica) {
                c.afiseaza();
            }
        }

        System.out.println();

        System.out.println("Sume si numar colaboratori pe tip:");
        for (TipColaborator tip : TipColaborator.values()) {
            double suma = 0;
            int numar = 0;
            for (Colaborator c : colaboratori) {
                if (c.getTip() == tip) {
                    suma += c.calculeazaVenitNetAnual();
                    numar++;
                }
            }
            if (numar > 0) {
                System.out.printf("%s: suma = %.2f lei, numar = %d%n", tip, suma, numar);
            } else {
                System.out.printf("%s: suma = nu lei, numar = null%n", tip);
            }
        }
    }
}