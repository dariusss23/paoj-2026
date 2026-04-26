package com.pao.project.banking.service;

import com.pao.project.banking.exception.ClientNegasitException;
import com.pao.project.banking.exception.ContNegasitException;
import com.pao.project.banking.exception.SoldInsuficientException;
import com.pao.project.banking.model.*;

import java.util.*;

public class ContService {

    private static ContService instance;
    private final Map<String, Cont> conturiDupaIban;

    private ContService() {
        conturiDupaIban = new LinkedHashMap<>();
    }

    public static ContService getInstance() {
        if (instance == null) {
            instance = new ContService();
        }
        return instance;
    }

    public Cont deschideCont(Client client, Cont.TipCont tip, String moneda, double soldInitial) throws ClientNegasitException {
        if (client == null) 
            throw new ClientNegasitException("null");
        String iban = genereazaIban();
        Cont cont = new Cont(iban, soldInitial, tip, moneda, client.getIdClient());
        conturiDupaIban.put(iban, cont);
        client.adaugaCont(cont);
        System.out.println("Cont deschis: " + cont.getIban() + " pentru " + client.getNumeComplet());
        return cont;
    }

    public Cont cautaDupaIban(String iban) throws ContNegasitException {
        if (iban == null || iban.isBlank())
            throw new IllegalArgumentException("IBAN invalid.");
        Cont c = conturiDupaIban.get(iban);
        if (c == null)
            throw new ContNegasitException(iban);
        return c;
    }

    public void depune(String iban, double suma) throws ContNegasitException {
        if (suma <= 0)
            throw new IllegalArgumentException("Suma de depus trebuie sa fie pozitiva.");
        Cont cont = cautaDupaIban(iban);
        cont.setSold(cont.getSold() + suma);
        Tranzactie t = new Tranzactie("NUMERAR", iban, suma, Tranzactie.TipTranzactie.DEPUNERE, "Depunere numerar");
        cont.adaugaTranzactie(t);
        System.out.printf("Depunere %.2f %s in contul %s. Sold nou: %.2f %s%n",suma, cont.getMoneda(), iban, cont.getSold(), cont.getMoneda());
    }

    public void retrage(String iban, double suma) throws ContNegasitException, SoldInsuficientException {
        if (suma <= 0)
            throw new IllegalArgumentException("Suma trebuie sa fie pozitiva.");

        Cont cont = cautaDupaIban(iban);
        if (cont.getSold() < suma)
            throw new SoldInsuficientException(cont.getSold(), suma);
        
        cont.setSold(cont.getSold() - suma);
        Tranzactie t = new Tranzactie(iban, "NUMERAR", suma, Tranzactie.TipTranzactie.RETRAGERE, "Retragere numerar");
        cont.adaugaTranzactie(t);
        System.out.printf("Retragere %.2f %s din contul %s. Sold ramas: %.2f %s%n", suma, cont.getMoneda(), iban, cont.getSold(), cont.getMoneda());
    }

    public void transfer(String ibanSursa, String ibanDest, double suma, String descriere) throws ContNegasitException, SoldInsuficientException {
        if (suma <= 0)
            throw new IllegalArgumentException("Suma trebuie sa fie pozitiva.");
        Cont sursa = cautaDupaIban(ibanSursa);
        Cont dest  = cautaDupaIban(ibanDest);
        if (sursa.getSold() < suma)
            throw new SoldInsuficientException(sursa.getSold(), suma);

        sursa.setSold(sursa.getSold() - suma);
        dest.setSold(dest.getSold() + suma);

        Tranzactie tDebitat = new Tranzactie(ibanSursa, ibanDest, suma, Tranzactie.TipTranzactie.TRANSFER_TRIMIS, descriere);
        Tranzactie tCreditat = new Tranzactie(ibanSursa, ibanDest, suma, Tranzactie.TipTranzactie.TRANSFER_PRIMIT, descriere);

        sursa.adaugaTranzactie(tDebitat);
        dest.adaugaTranzactie(tCreditat);

        System.out.printf("Transfer %.2f %s: %s -> %s ('%s')%n", suma, sursa.getMoneda(), ibanSursa, ibanDest, descriere);
    }

    public void afiseazaIstoricTranzactii(String iban) throws ContNegasitException {
        Cont cont = cautaDupaIban(iban);
        System.out.println("── Tranzactii pentru " + iban + " ──");
        List<Tranzactie> sortate = new ArrayList<>(cont.getTranzactii());
        Collections.sort(sortate); 
        
        if (sortate.isEmpty()) {
            System.out.println("(nicio tranzactie)");
        } else {
            for (Tranzactie t : sortate) {
                System.out.println(t);
            }
        }
    }

    public Card emiteCard(String iban, Card.TipCard tip, String dataExpirare, String cvv) throws ContNegasitException {
        Cont cont = cautaDupaIban(iban);
        String numar = genereazaNumarCard();
        Card card = new Card(numar, iban, tip, dataExpirare, cvv);
        cont.adaugaCard(card);
        System.out.println("Card emis: " + card + " pentru contul " + iban);
        return card;
    }

    public void seteazaStareCard(String iban, String numarCard, Card.StareCard stare) throws ContNegasitException {
        Cont cont = cautaDupaIban(iban);
        
        List<Card> carduri = cont.getCarduri();
        for (Card c : carduri) {
            if (c.getNumarCard().equals(numarCard)) {
                c.setStare(stare);
                System.out.println("Card " + numarCard + " => stare: " + stare);
                break;
            }
        }
    }

    public void calculeazaDobanda(String iban, double rataDobanda) throws ContNegasitException {
        Cont cont = cautaDupaIban(iban);
        if (cont.getTipCont() != Cont.TipCont.ECONOMII)
            throw new IllegalArgumentException("Dobanda se aplica doar conturilor de economii.");
        double dobanda = cont.getSold() * rataDobanda / 100.0;
        cont.setSold(cont.getSold() + dobanda);
        Tranzactie t = new Tranzactie("BANCA", iban, dobanda, Tranzactie.TipTranzactie.DEPUNERE, String.format("Dobanda %.2f%%", rataDobanda));
        cont.adaugaTranzactie(t);
        System.out.printf("Dobanda %.2f%% aplicata: +%.2f %s. Sold nou: %.2f %s%n", rataDobanda, dobanda, cont.getMoneda(), cont.getSold(), cont.getMoneda());
    }

    public void stergeCont(String iban) throws ContNegasitException {
        if (conturiDupaIban.remove(iban) == null) {
            throw new ContNegasitException(iban);
        }
        System.out.println("Cont inchis: " + iban);
    }

    public List<Cont> listeazaToate() {
        return new ArrayList<>(conturiDupaIban.values());
    }

    private static int contorIban = 1000;
    private String genereazaIban() {
        return "RO23PAO0" + String.format("%016d", ++contorIban);
    }

    private static int contorCard = 4000_0000;
    private String genereazaNumarCard() {
        contorCard += 1231;
        return "4532" + String.format("%012d", contorCard);
    }
}