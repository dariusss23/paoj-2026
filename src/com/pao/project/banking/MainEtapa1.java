package com.pao.project.banking;

import com.pao.project.banking.exception.*;
import com.pao.project.banking.model.*;
import com.pao.project.banking.service.*;

import java.time.LocalDate;

public class MainEtapa1 {

    public static void main(String[] args) {
        ClientService clientService = ClientService.getInstance();
        ContService contService = ContService.getInstance();
        AngajatService angajatService = AngajatService.getInstance();

        System.out.println("==================================");
        System.out.println("SISTEM BANCAR - DEMO");
        System.out.println("==================================");

        Adresa adr1 = new Adresa("Str. Florilor 12", "Ploiesti", "Prahova", "Romania");
        Adresa adr2 = new Adresa("Bd. Unirii 45", "Bucuresti", "Ilfov", "Romania");
        Adresa adr3 = new Adresa("Calea Victoriei 1", "Bucuresti", "Ilfov", "Romania");

        Angajat angajat = new Angajat("Popescu", "Ion", "1800101123456", LocalDate.of(1980, 1, 1), "0721000001", "ion.popescu@banca.ro", adr3, "Consilier Clienti", 5500.0, "Retail");
        angajatService.adaugaAngajat(angajat);

        Director director = new Director("Gheorghe", "Vasile", "1750202123456", LocalDate.of(1975, 2, 2), "0722111222", "vasile.gheorghe@banca.ro", adr2, "Director Sucursala", 12000.0, "Management", "Retail", 20000.0);
        angajatService.adaugaAngajat(director);

        ClientFizic cf1 = new ClientFizic("Ionescu", "Maria", "2901215290015", LocalDate.of(1990, 12, 15), "0740123456", "maria.ionescu@email.ro", adr1, false);
        ClientJuridic cj1 = new ClientJuridic("TechRo", "RO12345678", ClientJuridic.TipFirma.SRL, "IT & Software", "Georgescu", "Alexandru", "0730987654", "alex.georgescu@techro.ro", adr2);

        System.out.println("==================================");
        System.out.println("1. INREGISTRARE CLIENTI NOI");
        System.out.println("==================================");

        clientService.adaugaClient(cf1);
        clientService.adaugaClient(cj1);

        System.out.println("==================================");
        System.out.println("2. DESCHIDERE CONTURI NOI PENTRU CLIENTI");
        System.out.println("==================================");

        Cont contCurentCf1 = null;
        Cont contEconomiiCf1 = null;
        Cont contCurentCj1 = null;
        try {
            contCurentCf1 = contService.deschideCont(cf1, Cont.TipCont.CURENT, "RON", 500.0);
            contEconomiiCf1 = contService.deschideCont(cf1, Cont.TipCont.ECONOMII, "RON", 2000.0);
            contCurentCj1 = contService.deschideCont(cj1, Cont.TipCont.CURENT, "RON", 10000.0);
        } catch (ClientNegasitException e) {
            System.err.println("Eroare: " + e.getMessage());
        }

        System.out.println("==================================");
        System.out.println("3. EMITERE CARDURI");
        System.out.println("==================================");

        Card cardDebit = null;
        try {
            if (contCurentCf1 != null)
                cardDebit = contService.emiteCard(contCurentCf1.getIban(), Card.TipCard.DEBIT, "12/28", "123");
            if (contCurentCj1 != null)
                contService.emiteCard(contCurentCj1.getIban(), Card.TipCard.CREDIT, "06/29", "456");
        } catch (ContNegasitException e) {
            System.err.println("Eroare: " + e.getMessage());
        }

        System.out.println("==================================");
        System.out.println("4. DEPUNERE NUMERAR");
        System.out.println("==================================");

        try {
            if (contCurentCf1 != null)
                contService.depune(contCurentCf1.getIban(), 1500.0);
        } catch (ContNegasitException e) {
            System.err.println("Eroare: " + e.getMessage());
        }

        System.out.println("==================================");
        System.out.println("5. RETRAGERE NUMERAR");
        System.out.println("==================================");

        try {
            if (contCurentCf1 != null)
                contService.retrage(contCurentCf1.getIban(), 300.0);

            System.out.println("\n[Test] Incercare retragere 999999 RON...");
            if (contCurentCf1 != null)
                contService.retrage(contCurentCf1.getIban(), 999999.0);
        } catch (SoldInsuficientException e) {
            System.err.println(e.getMessage());
        } catch (ContNegasitException e) {
            System.err.println("Eroare: " + e.getMessage());
        }

        System.out.println("==================================");
        System.out.println("6. TRANSFER INTRE CONTURI");
        System.out.println("==================================");
        try {
            if (contCurentCf1 != null && contCurentCj1 != null)
                contService.transfer(contCurentCf1.getIban(), contCurentCj1.getIban(), 250.0, "Plata servicii IT");
        } catch (ContNegasitException | SoldInsuficientException e) {
            System.err.println("Eroare: " + e.getMessage());
        }

        System.out.println("==================================");
        System.out.println("7. BLOCARE/DEBLOCARE CARD");
        System.out.println("==================================");

        try {
            if (contCurentCf1 != null && cardDebit != null) {
                contService.seteazaStareCard(contCurentCf1.getIban(), cardDebit.getNumarCard(), Card.StareCard.BLOCAT);
                contService.seteazaStareCard(contCurentCf1.getIban(), cardDebit.getNumarCard(), Card.StareCard.ACTIV);
            }
        } catch (ContNegasitException e) {
            System.err.println("Eroare: " + e.getMessage());
        }

        System.out.println("==================================");
        System.out.println("8. AFISARE ISTORIC TRANZACTII");
        System.out.println("==================================");

        try {
            if (contCurentCf1 != null)
                contService.afiseazaIstoricTranzactii(contCurentCf1.getIban());
        } catch (ContNegasitException e) {
            System.err.println("Eroare: " + e.getMessage());
        }

        System.out.println("==================================");
        System.out.println("9. CAUTARE CLIENTI DUPA CNP/CUI");
        System.out.println("==================================");

        try {
            ClientFizic gasitFizic = clientService.cautaDupaCnp("2901215290015");
            System.out.println("Client fizic gasit: " + gasitFizic);

            ClientJuridic gasitJuridic = clientService.cautaDupaCui("RO12345678");
            System.out.println("Client juridic gasit: " + gasitJuridic);

            System.out.println("\n[Test] Cautare CNP inexistent...");
            clientService.cautaDupaCnp("9999999999999");

        } catch (ClientNegasitException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("==================================");
        System.out.println("10. AFISARE CONTURI DETINUTE DE CLIENTI");
        System.out.println("==================================");

        try {
            clientService.afiseazaConturiClient(cf1.getIdClient());
            clientService.afiseazaConturiClient(cj1.getIdClient());
        } catch (ClientNegasitException e) {
            System.err.println("Eroare: " + e.getMessage());
        }

        System.out.println("==================================");
        System.out.println("11. CALCUL DOBANDA CONT ECONOMII");
        System.out.println("==================================");

        try {
            if (contEconomiiCf1 != null)
                contService.calculeazaDobanda(contEconomiiCf1.getIban(), 3.5);
        } catch (ContNegasitException e) {
            System.err.println("Eroare: " + e.getMessage());
        }

        System.out.println("==================================");
        System.out.println("12. INCHIDERE CONTURI SI STERGERE CLIENTI");
        System.out.println("==================================");

        try {
            if (contCurentCj1 != null)
                contService.stergeCont(contCurentCj1.getIban());
            clientService.stergeClient(cj1.getIdClient());

            System.out.println("Clienti ramasi: " + clientService.numarClienti());
        } catch (ClientNegasitException | ContNegasitException e) {
            System.err.println("Eroare: " + e.getMessage());
        }

        System.out.println("==================================");
        System.out.println("SUMAR FINAL");
        System.out.println("==================================");
        System.out.println("Clienti activi: " + clientService.numarClienti());
        System.out.println("Angajati: " + angajatService.numarAngajati());
        System.out.println("Conturi totale: " + contService.listeazaToate().size());
        System.out.println(angajat);
        System.out.println(director);
    }
}
