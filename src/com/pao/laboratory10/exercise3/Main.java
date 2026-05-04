package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.TipTranzactie;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Date de test hardcodate
        List<Tranzactie> lista = Arrays.asList(
            new Tranzactie(1, 1000.0, "2024-01-01", TipTranzactie.CREDIT, "CONT1"),
            new Tranzactie(2, 200.0,  "2024-01-05", TipTranzactie.DEBIT,  "CONT2"),
            new Tranzactie(3, 500.0,  "2024-02-10", TipTranzactie.CREDIT, "CONT1"),
            new Tranzactie(4, 300.0,  "2024-02-15", TipTranzactie.DEBIT,  "CONT3"),
            new Tranzactie(5, 1500.0, "2024-03-01", TipTranzactie.CREDIT, "CONT2"),
            new Tranzactie(6, 100.0,  "2024-03-05", TipTranzactie.DEBIT,  "CONT1"),
            new Tranzactie(7, 2500.0, "2024-03-10", TipTranzactie.CREDIT, "CONT4"),
            new Tranzactie(8, 50.0,   "2024-01-20", TipTranzactie.DEBIT,  "CONT2"),
            new Tranzactie(9, 800.0,  "2024-02-20", TipTranzactie.CREDIT, "CONT3"),
            new Tranzactie(10, 400.0, "2024-03-25", TipTranzactie.DEBIT,  "CONT4")
        );

        System.out.println("\n# 1. Toate tranzactiile CREDIT:");
        lista.stream().filter(t -> t.getTip() == TipTranzactie.CREDIT).forEach(t -> System.out.println(t));

        double total = lista.stream().mapToDouble(t -> t.getSuma()).sum();
        System.out.println("\n# 2. Total procesat: " + total + " RON");

        System.out.println("\n# 3. Suma pe luna (yyyy-MM):");
        Map<String, Double> sumaPeLuna = lista.stream()
            .collect(Collectors.groupingBy(
                t -> t.getData().substring(0, 7),
                Collectors.summingDouble(t -> t.getSuma())
            ));
        sumaPeLuna.forEach((luna, s) -> System.out.println(luna + ": " + s + " RON"));

        System.out.println("\n# 4. Top 3 tranzactii:");
        lista.stream().sorted((t1, t2) -> Double.compare(t2.getSuma(), t1.getSuma())).limit(3).forEach(t -> System.out.println(t));

        System.out.println("\n# 5. Conturi sursa unice:");
        List<String> conturi = lista.stream().map(t -> t.getContSursa()).distinct().collect(Collectors.toList());
        System.out.println(conturi);

        double medie = lista.stream().mapToDouble(t -> t.getSuma()).average().orElse(0.0);
        System.out.printf("\n# 6. Suma medie: %.2f RON\n", medie);

        System.out.println("\n# 7. EXTRAS DE CONT PE LUNI:");
        lista.stream().collect(Collectors.groupingBy(t -> t.getData().substring(0, 7))).forEach((luna, subLista) -> {
                double totalLuna = subLista.stream().mapToDouble(t -> t.getSuma()).sum();
                System.out.println("Luna " + luna + ": " + subLista.size() + " tranzactii, Total: " + totalLuna + " RON");
        });
    }
}