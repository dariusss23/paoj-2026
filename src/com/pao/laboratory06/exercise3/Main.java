package com.pao.laboratory06.exercise3;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Inginer[] ingineri = {
            new Inginer("Popescu", "Ion", "0722", 5000),
            new Inginer("Ionescu", "Ana", "0733", 7500),
            new Inginer("Avram", "Dan", "0744", 6000)
        };

        Arrays.sort(ingineri);
        for (Inginer i : ingineri) 
            System.out.println(i);

        System.out.println();

        Arrays.sort(ingineri, new ComparatorInginerSalariu());
        for (Inginer i : ingineri) 
            System.out.println(i);

        System.out.println();

        PlataOnline plataInginer = ingineri[0];
        plataInginer.autentificare("user1", "pass1");
        // plataInginer.salariu;

        System.out.println();

        PersoanaJuridica firma = new PersoanaJuridica("Microsoft", "0711223344");
        PlataOnlineSMS plataSMS = firma;

        System.out.println();
        
        plataSMS.trimiteSMS("Confirmare plata factura #1");
        plataSMS.trimiteSMS("Alerta sold scazut");

        System.out.println();
        
        System.out.println("SMS invalid (gol): " + plataSMS.trimiteSMS("")); 
        
        System.out.println("Mesaje stocate in Microsoft: " + firma.getSmsTrimise());

        System.out.println("\n--- Constante Financiare ---");
        System.out.println("TVA curent: " + (ConstanteFinanciare.TVA.getValoare() * 100) + "%");

        System.out.println("\n--- Testare Edge Cases ---");
        try {
            plataInginer.trimiteSMS("Test SMS");
        } catch (UnsupportedOperationException e) {
            System.err.println("Eroare asteptata: " + e.getMessage());
        }

        try {
            plataInginer.autentificare(null, "123");
        } catch (IllegalArgumentException e) {
            System.err.println("Eroare validare: " + e.getMessage());
        }

        PersoanaJuridica firmaFaraTel = new PersoanaJuridica("NoPhone SRL", null);
        boolean rezultatSMS = firmaFaraTel.trimiteSMS("Salut");
        System.out.println("Trimitere SMS fara telefon: " + rezultatSMS + " (Asteptat: false)");
    }
}