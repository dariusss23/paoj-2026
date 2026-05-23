package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Transaction> data = List.of(
            new Transaction(1, new BigDecimal("150.00"), LocalDate.now(), "RO", "WEB"),
            new Transaction(2, new BigDecimal("200.00"), LocalDate.now(), "UK", "APP"),
            new Transaction(3, new BigDecimal("150.00"), LocalDate.now(), "RO", "WEB"),
            new Transaction(4, new BigDecimal("50.00"), LocalDate.now(), "US", "APP"),
            new Transaction(5, new BigDecimal("300.00"), LocalDate.now(), "UK", "API"),
            new Transaction(6, new BigDecimal("300.00"), LocalDate.now(), "US", "WEB"),
            new Transaction(7, new BigDecimal("10.00"), LocalDate.now(), "RO", "APP")
        );

        Snapshot snap = data.stream().collect(CustomCollectors.toSnapshot(4));

        System.out.println("=== Interogare 1: Top 4 Tranzactii (Suma desc, apoi ID) ===");
        snap.getTopTransactions().forEach(System.out::println);

        System.out.println("\n=== Interogare 2: Tranzactii pe Tari (Descrescator) ===");
        snap.getCountByCountry().entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));

        System.out.println("\n=== Interogare 3: Tranzactii pe Canale (Descrescator) ===");
        snap.getCountByChannel().entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
            
        System.out.println("\n[Info] Suma totala procesata: " + snap.getTotalAmount());
    }
}