package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.TipTranzactie;
import com.pao.laboratory10.exercise1.Tranzactie;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        List<Tranzactie> lista = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int id = scanner.nextInt();
            double suma = Double.parseDouble(scanner.next());
            String data = scanner.next();
            TipTranzactie tip = TipTranzactie.valueOf(scanner.next());
            lista.add(new Tranzactie(id, suma, data, tip));
        }

        while (scanner.hasNext()) {
            String comanda = scanner.next();

            switch (comanda) {
                case "UNIQUE_IDS":
                    LinkedHashSet<Integer> setId = new LinkedHashSet<>();
                    for (Tranzactie t : lista) {
                        setId.add(t.getId());
                    }
                    System.out.println("IDs unice (" + setId.size() + "): " + setId);
                    break;

                case "MONTHLY_REPORT":
                    TreeMap<String, double[]> raport = new TreeMap<>();
                    for (Tranzactie t : lista) {
                        String luna = t.getData().substring(0, 7);
                        if (!raport.containsKey(luna)) {
                            raport.put(luna, new double[2]);
                        }
                        double[] sume = raport.get(luna);
                        if (t.getTip() == TipTranzactie.CREDIT) {
                            sume[0] += t.getSuma();
                        } else {
                            sume[1] += t.getSuma();
                        }
                    }
                    for (String luna : raport.keySet()) {
                        double[] s = raport.get(luna);
                        System.out.printf("%s: CREDIT %.2f RON, DEBIT %.2f RON\n", luna, s[0], s[1]);
                    }
                    break;

                case "TOP":
                    int nTop = scanner.nextInt();
                    List<Tranzactie> copieTop = new ArrayList<>(lista);

                    Collections.sort(copieTop, Comparator.comparingDouble(Tranzactie::getSuma).reversed());
                    
                    System.out.println("Top " + nTop + ":");
                    for (int i = 0; i < nTop && i < copieTop.size(); i++) {
                        System.out.println(copieTop.get(i));
                    }
                    break;

                case "SORT_ASC":
                    Collections.sort(lista, Comparator.comparingDouble(Tranzactie::getSuma));
                    printLista(lista);
                    break;

                case "SORT_DESC":
                    Collections.sort(lista, Comparator.comparingDouble(Tranzactie::getSuma).reversed());
                    printLista(lista);
                    break;

                case "REVERSE":
                    Collections.reverse(lista);
                    printLista(lista);
                    break;

                case "MIN_MAX":
                    if (!lista.isEmpty()) {
                        Tranzactie min = Collections.min(lista, Comparator.comparingDouble(Tranzactie::getSuma));
                        Tranzactie max = Collections.max(lista, Comparator.comparingDouble(Tranzactie::getSuma));
                        System.out.println("MIN: " + min);
                        System.out.println("MAX: " + max);
                    }
                    break;

                case "CME_DEMO":
                    try {
                        for (Tranzactie t : lista) {
                            lista.remove(t);
                        }
                    } catch (ConcurrentModificationException e) {
                        System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                    }
                    break;
            }
        }
        scanner.close();
    }

    private static void printLista(List<Tranzactie> l) {
        for (Tranzactie t : l) {
            System.out.println(t);
        }
    }
}