package com.pao.project.banking.service;

import com.pao.project.banking.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class AdvancedBankingService {
    private static AdvancedBankingService instance;

    private AdvancedBankingService() {
    }

    public static AdvancedBankingService getInstance() {
        if (instance == null) {
            instance = new AdvancedBankingService();
        }
        return instance;
    }

    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    public void transferCuTranzactie(String ibanSursa, String ibanDest, double suma, String descriere) throws SQLException {
        Connection connection;
        try {
            connection = getConn();
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }

        connection.setAutoCommit(false);
        try {
            String checkSql = "SELECT sold FROM cont WHERE iban = ?";
            double soldSursa = 0;
            try (PreparedStatement pstmt = connection.prepareStatement(checkSql)) {
                pstmt.setString(1, ibanSursa);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        soldSursa = rs.getDouble("sold");
                    } else {
                        throw new SQLException("Contul sursa nu exista!");
                    }
                }
            }

            if (soldSursa < suma) {
                throw new SQLException("Sold insuficient!");
            }

            String updateSursa = "UPDATE cont SET sold = sold - ? WHERE iban = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(updateSursa)) {
                pstmt.setDouble(1, suma);
                pstmt.setString(2, ibanSursa);
                pstmt.executeUpdate();
            }

            String updateDest = "UPDATE cont SET sold = sold + ? WHERE iban = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(updateDest)) {
                pstmt.setDouble(1, suma);
                pstmt.setString(2, ibanDest);
                pstmt.executeUpdate();
            }

            String insertTx1 = "INSERT INTO tranzactie (id_tranzactie, iban_sursa, iban_destinatie, suma, tip_tranzactie, data_ora, descriere) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertTx1)) {
                pstmt.setString(1, UUID.randomUUID().toString());
                pstmt.setString(2, ibanSursa);
                pstmt.setString(3, ibanDest);
                pstmt.setDouble(4, suma);
                pstmt.setString(5, "TRANSFER_TRIMIS");
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setString(7, descriere);
                pstmt.executeUpdate();
            }

            String insertTx2 = "INSERT INTO tranzactie (id_tranzactie, iban_sursa, iban_destinatie, suma, tip_tranzactie, data_ora, descriere) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertTx2)) {
                pstmt.setString(1, UUID.randomUUID().toString());
                pstmt.setString(2, ibanSursa);
                pstmt.setString(3, ibanDest);
                pstmt.setDouble(4, suma);
                pstmt.setString(5, "TRANSFER_PRIMIT");
                pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                pstmt.setString(7, descriere);
                pstmt.executeUpdate();
            }

            connection.commit();
            System.out.println("Transfer efectuat cu succes.");
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // JOIN 1
    public void afiseazaConturiCuNumeClient() throws SQLException {
        String sql = "SELECT c.iban, c.sold, c.moneda, f.nume, f.prenume " +
                     "FROM cont c JOIN client_fizic f ON c.id_proprietar = f.id_client";
        try {
            Connection connection = getConn();
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {
                System.out.println("--- Conturi cu Nume Client ---");
                while (rs.next()) {
                    System.out.printf("IBAN: %s | Sold: %.2f %s | Client: %s %s%n", rs.getString("iban"), rs.getDouble("sold"), rs.getString("moneda"), rs.getString("nume"), rs.getString("prenume"));
                }
            }
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }

    // JOIN 2
    public void afiseazaCarduriCuInfoProprietar() throws SQLException {
        String sql = "SELECT cd.numar_card, cd.tip_card, cd.stare, c.iban, f.nume, f.prenume " +
                     "FROM card cd " +
                     "JOIN cont c ON cd.iban_cont = c.iban " +
                     "JOIN client_fizic f ON c.id_proprietar = f.id_client";
        try {
            Connection connection = getConn();
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {
                System.out.println("--- Carduri active ---");
                while (rs.next()) {
                    System.out.printf("Card: %s (%s) | Stare: %s | Cont: %s | Proprietar: %s %s%n", rs.getString("numar_card"), rs.getString("tip_card"), rs.getString("stare"), rs.getString("iban"), rs.getString("nume"), rs.getString("prenume"));
                }
            }
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }

    // JOIN 3
    public void afiseazaTranzactiiCuNumeSursa() throws SQLException {
        String sql = "SELECT t.id_tranzactie, t.suma, t.tip_tranzactie, f.nume, f.prenume " +
                     "FROM tranzactie t " +
                     "JOIN cont c ON t.iban_sursa = c.iban " +
                     "JOIN client_fizic f ON c.id_proprietar = f.id_client";
        try {
            Connection connection = getConn();
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                    ResultSet rs = stmt.executeQuery()) {
                System.out.println("--- Tranzactii detaliate ---");
                while (rs.next()) {
                    System.out.printf("TRX: %s | Tip: %s | Suma: %.2f | Expeditor: %s %s%n", rs.getString("id_tranzactie"), rs.getString("tip_tranzactie"), rs.getDouble("suma"), rs.getString("nume"), rs.getString("prenume"));
                }
            }
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }
}
