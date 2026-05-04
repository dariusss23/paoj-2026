package com.pao.laboratory10.exercise1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<Tranzactie> coada = new LinkedList<>();

        while (scanner.hasNext()) {
            String comanda = scanner.next();

            if (comanda.equalsIgnoreCase("ENQUEUE")) {
                coada.addLast(citesteTranzactie(scanner));
            }
            if (comanda.equalsIgnoreCase("PUSH")) {
                coada.addFirst(citesteTranzactie(scanner));
            }
            if (comanda.equalsIgnoreCase("DEQUEUE")) {
                if (coada.isEmpty() == true) {
                    System.out.println("Coada goala.");
                }
                else {
                    System.out.println("Procesat: " + coada.removeFirst());
                }
            }
            if (comanda.equalsIgnoreCase("POP")) {
                if (coada.isEmpty() == true){
                    System.out.println("Coada goala.");
                }
                else {
                    System.out.println("Extras: " + coada.removeFirst());
                }
            }
            if (comanda.equalsIgnoreCase("SIZE")){
                System.out.println("Dimensiune coada: " + coada.size());
            }
            if (comanda.equalsIgnoreCase("PRINT")) {
                for (Tranzactie t : coada){
                    System.out.println(t);
                }
            }
            if (comanda.equalsIgnoreCase("REMOVE_DEBIT")) {
                int nr = 0;
                Iterator<Tranzactie> itDebit = coada.iterator();
                while (itDebit.hasNext()) {
                    if (itDebit.next().getTip() == TipTranzactie.DEBIT) {
                        itDebit.remove();
                        nr++;
                    }
                }
                System.out.println("Eliminat " + nr + " tranzactii DEBIT.");
            }
            if (comanda.equalsIgnoreCase("REMOVE_BELOW")) {
                double prag = Double.parseDouble(scanner.next());
                int nr = 0;
                Iterator<Tranzactie> itPrag = coada.iterator();
                while (itPrag.hasNext()) {
                    if (itPrag.next().getSuma() < prag){
                        itPrag.remove();
                        nr++;
                    }
                }
                System.out.printf("Eliminat %d tranzactii sub %.2f RON.\n", nr, prag);
            }
        }
        scanner.close();
    }

    private static Tranzactie citesteTranzactie(Scanner sc) {
        int id = sc.nextInt();
        double suma = Double.parseDouble(sc.next());
        String data = sc.next();
        TipTranzactie tip = TipTranzactie.valueOf(sc.next());
        return new Tranzactie(id, suma, data, tip);
    }
}
