package com.pao.project.banking;

import com.pao.project.banking.model.Adresa;
import com.pao.project.banking.model.ClientFizic;
import com.pao.project.banking.model.Cont;
import com.pao.project.banking.model.Card;
import com.pao.project.banking.repository.CardRepository;
import com.pao.project.banking.repository.ClientFizicRepository;
import com.pao.project.banking.repository.ContRepository;
import com.pao.project.banking.service.AdvancedBankingService;
import com.pao.project.banking.service.AuditService;
import com.pao.project.banking.util.DatabaseConnection;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MainEtapa2 {
    public static void main(String[] args) throws Exception {
        AuditService audit = AuditService.getInstance();
        ClientFizicRepository clientRepo = new ClientFizicRepository();
        ContRepository contRepo = new ContRepository();
        CardRepository cardRepo = new CardRepository();
        AdvancedBankingService bankingService = AdvancedBankingService.getInstance();

        System.out.println("=== SISTEM BANCAR JDBC — Demo Etapa 2 ===\n");

        System.out.println("==================================");
        System.out.println("1. ADAUGARE CLIENT FIZIC");
        System.out.println("==================================");
        Adresa adr1 = new Adresa("Str. Florilor 12", "Ploiesti", "Prahova", "Romania");
        ClientFizic client1 = new ClientFizic("Ionescu", "Maria", "2901214290015", LocalDate.of(1990, 12, 15), "0740123456", "maria@email.ro", adr1, false);
        clientRepo.save(client1);
        audit.log("add_client");
        System.out.println("1. Client adaugat: " + client1.getNume() + " " + client1.getPrenume() + " (ID: " + client1.getIdClient() + ")");

        System.out.println("==================================");
        System.out.println("2. ADAUGARE CONTURI PENTRU CLIENT");
        System.out.println("==================================");
        Cont cont1 = new Cont("RO23PAO00000000000000001", 5000.0, Cont.TipCont.CURENT, "RON", client1.getIdClient());
        Cont cont2 = new Cont("RO23PAO00000000000000002", 1500.0, Cont.TipCont.ECONOMII, "RON", client1.getIdClient());
        contRepo.save(cont1);
        audit.log("add_cont");
        contRepo.save(cont2);
        audit.log("add_cont");
        System.out.println("2. Conturi adaugate pentru client: " + cont1.getIban() + ", " + cont2.getIban());

        System.out.println("==================================");
        System.out.println("3. EMITERE CARD DEBIT PENTRU CONT");
        System.out.println("==================================");
        Card card1 = new Card("4532000000000001", cont1.getIban(), Card.TipCard.DEBIT, "12/28", "123");
        cardRepo.save(card1);
        audit.log("add_card");
        System.out.println("3. Card emis: " + card1.getNumarCard());

        System.out.println("==================================");
        System.out.println("4. AFISARE TOATE CONTURILE");
        System.out.println("==================================");
        List<Cont> toateConturile = contRepo.findAll();
        audit.log("list_conturi");
        System.out.println("4. Toate conturile (" + toateConturile.size() + "):");
        for (Cont c : toateConturile) {
            System.out.println("   " + c.getIban() + " - Sold: " + c.getSold());
        }

        System.out.println("==================================");
        System.out.println("5. CAUTARE CLIENT DUPA ID");
        System.out.println("==================================");
        Optional<ClientFizic> clientOptional = clientRepo.findById(client1.getIdClient());
        if (clientOptional.isPresent()) {
            ClientFizic c = clientOptional.get();
            System.out.println("5. Client gasit: " + c.getNume());
        } else {
            System.out.println("5. Client negasit.");
        }
        audit.log("find_client_by_id");

        System.out.println("==================================");
        System.out.println("6. ACTUALIZARE SOLD CONT");
        System.out.println("==================================");
        cont1.setSold(5200.0);
        contRepo.update(cont1);
        audit.log("update_cont");
        System.out.println("6. Cont actualizat, noul sold: " + cont1.getSold());

        System.out.println("==================================");
        System.out.println("7. TRANSFER CU TRANZACTIE (DEMO ROLLBACK)");
        System.out.println("==================================");
        try {
            bankingService.transferCuTranzactie(cont1.getIban(), cont2.getIban(), 200.0, "Alimentare economii");
            audit.log("transfer_bancar");
            System.out.println("7. Transfer efectuat cu succes.");
        } catch (SQLException e) {
            System.out.println("[TX] Eroare transfer: " + e.getMessage());
        }

        // Test rollback
        try {
            bankingService.transferCuTranzactie(cont1.getIban(), cont2.getIban(), 999999.0, "Test rollback");
        } catch (SQLException e) {
            System.out.println("[ROLLBACK] " + e.getMessage());
        }

        System.out.println("==================================");
        System.out.println("8. AFISARE CONTURI CU NUME CLIENT (JOIN 1)");
        System.out.println("==================================");
        bankingService.afiseazaConturiCuNumeClient();
        audit.log("report_conturi_clienti");

        System.out.println("==================================");
        System.out.println("9. AFISARE CARDURI CU INFO PROPRIETAR (JOIN 2)");
        System.out.println("==================================");
        bankingService.afiseazaCarduriCuInfoProprietar();
        audit.log("report_carduri_proprietari");

        System.out.println("==================================");
        System.out.println("10. AFISARE TRANZACTII CU NUME SURSA");
        System.out.println("==================================");
        bankingService.afiseazaTranzactiiCuNumeSursa();
        audit.log("report_tranzactii_detaliat");

        System.out.println("\n=== Demo finalizat. Verifica audit.csv ===");
        DatabaseConnection.getInstance().close();
    }
}
