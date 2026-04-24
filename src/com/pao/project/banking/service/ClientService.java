package com.pao.project.banking.service;

import com.pao.project.banking.exception.ClientNegasitException;
import com.pao.project.banking.model.Client;
import com.pao.project.banking.model.ClientFizic;
import com.pao.project.banking.model.ClientJuridic;
import com.pao.project.banking.model.Cont;

import java.util.*;

public class ClientService {

    private static ClientService instance;
    private final Map<Integer, Client> clientiDupaId;
    private final TreeSet<String> indexNumeClienti;

    private ClientService() {
        clientiDupaId = new LinkedHashMap<>();
        indexNumeClienti = new TreeSet<>();
    }

    public static ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    public void adaugaClient(Client client) {
        if (client == null)
            throw new IllegalArgumentException("Clientul nu poate fi null.");
        clientiDupaId.put(client.getIdClient(), client);
        indexNumeClienti.add(client.getNumeComplet());
        System.out.println("Client inregistrat: " + client.getNumeComplet() + " (ID: " + client.getIdClient() + ")");
    }

    public Client cautaDupaId(int id) throws ClientNegasitException {
        Client c = clientiDupaId.get(id);
        if (c == null) 
            throw new ClientNegasitException("ID=" + id);
        return c;
    }

    public ClientFizic cautaDupaCnp(String cnp) throws ClientNegasitException {
        if (cnp == null || cnp.isBlank())
            throw new IllegalArgumentException("CNP invalid.");

        for (Client c : clientiDupaId.values()) {
            if (c instanceof ClientFizic) {
                ClientFizic cf = (ClientFizic) c;
                if (cf.getCnp().equals(cnp)) {
                    return cf;
                }
            }
        }
        throw new ClientNegasitException("CNP=" + cnp);
    }

    public ClientJuridic cautaDupaCui(String cui) throws ClientNegasitException {
        if (cui == null || cui.isBlank())
            throw new IllegalArgumentException("CUI invalid.");

        for (Client c : clientiDupaId.values()) {
            if (c instanceof ClientJuridic) {
                ClientJuridic cj = (ClientJuridic) c;
                if (cj.getCui().equals(cui)) {
                    return cj;
                }
            }
        }
        throw new ClientNegasitException("CUI=" + cui);
    }

    public void stergeClient(int id) throws ClientNegasitException {
        Client c = cautaDupaId(id);
        clientiDupaId.remove(id);
        indexNumeClienti.remove(c.getNumeComplet());
        System.out.println("Client eliminat: " + c.getNumeComplet());
    }

    public List<Client> listeazaToti() {
        return new ArrayList<>(clientiDupaId.values());
    }

    public void afiseazaConturiClient(int idClient) throws ClientNegasitException {
        Client c = cautaDupaId(idClient);
        System.out.println("── Conturi pentru " + c.getNumeComplet() + " ──");
        
        List<Cont> listaConturi = c.getConturi();
        
        if (listaConturi.isEmpty()) {
            System.out.println("(niciun cont deschis)");
        } else {
            for (Cont cont : listaConturi) {
                System.out.println(cont);
            }
        }
    }

    public int numarClienti() {
        return clientiDupaId.size();
    }

    public void afiseazaNumeClientiAlfabetic() {
        System.out.println("── Index Alfabetic Clienti ──");
        if (indexNumeClienti.isEmpty()) {
            System.out.println("(nu exista clienti)");
        } else {
            for (String nume : indexNumeClienti) {
                System.out.println("  " + nume);
            }
        }
    }
}