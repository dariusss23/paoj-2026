package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        List<Tranzactie> listaInitiala = new ArrayList<>();

        for (int i=0; i<n; i++) {
            int id = scanner.nextInt();
            double suma = scanner.nextDouble();
            String data = scanner.next();
            String sursa = scanner.next();
            String dest = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());

            Tranzactie t = new Tranzactie(id, suma, data, sursa, dest, tip);
            listaInitiala.add(t);
        }

        new File("output").mkdirs(); 
        
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("output/lab09_ex1.ser"));
        oos.writeObject(listaInitiala);
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("output/lab09_ex1.ser"));
        List<Tranzactie> listaCitita = (List<Tranzactie>) ois.readObject();
        ois.close();

        while (scanner.hasNext()) {
            String comanda = scanner.next();

            if (comanda.equals("LIST")) {
                for (Tranzactie t : listaCitita) {
                    System.out.println(t);
                }
            } 
            else if (comanda.equals("FILTER")) {
                String luna = scanner.next();
                boolean amGasitCeva = false;
                for (Tranzactie t : listaCitita) {
                    if (t.getData().startsWith(luna)) {
                        System.out.println(t);
                        amGasitCeva = true;
                    }
                }
                if (!amGasitCeva) {
                    System.out.println("Niciun rezultat.");
                }
            } 
            else if (comanda.equals("NOTE")) {
                int idCautat = scanner.nextInt();
                boolean gasit = false;
                for (Tranzactie t : listaCitita) {
                    if (t.getId() == idCautat) {
                        System.out.println("NOTE[" + idCautat + "]: " + t.getNote());
                        gasit = true;
                        break;
                    }
                }
                if (!gasit) {
                    System.out.println("NOTE[" + idCautat + "]: not found");
                }
            }
        }
        scanner.close();
    }
}