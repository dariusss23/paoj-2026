package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

public class CustomCollectors {

    public static Collector<Transaction, ?, Snapshot> toSnapshot(int topN) {
        class Agg {
            Map<String, Long> byCountry = new HashMap<>();
            Map<String, Long> byChannel = new HashMap<>();
            BigDecimal totalAmount = BigDecimal.ZERO;
            List<Transaction> transactions = new ArrayList<>();
        }

        return Collector.of(
            Agg::new,
            (agg, tx) -> {
                agg.byCountry.merge(tx.getCountry(), 1L, Long::sum);
                agg.byChannel.merge(tx.getChannel(), 1L, Long::sum);
                agg.totalAmount = agg.totalAmount.add(tx.getAmount());
                agg.transactions.add(tx);
            },
            (agg1, agg2) -> {
                agg2.byCountry.forEach((k, v) -> agg1.byCountry.merge(k, v, Long::sum));
                agg2.byChannel.forEach((k, v) -> agg1.byChannel.merge(k, v, Long::sum));
                agg1.totalAmount = agg1.totalAmount.add(agg2.totalAmount);
                agg1.transactions.addAll(agg2.transactions);
                return agg1;
            },
            agg -> {
                List<Transaction> topTransactions = agg.transactions.stream()
                    .sorted(Comparator.comparing(Transaction::getAmount).reversed()
                            .thenComparing(Transaction::getId))
                    .limit(topN)
                    .toList();
                
                return new Snapshot(agg.byCountry, agg.byChannel, agg.totalAmount, topTransactions);
            },
            Collector.Characteristics.UNORDERED
        );
    }
}