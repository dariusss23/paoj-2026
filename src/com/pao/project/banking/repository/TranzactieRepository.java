package com.pao.project.banking.repository;

import com.pao.project.banking.model.Tranzactie;
import com.pao.project.banking.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TranzactieRepository implements Repository<Tranzactie, String> {

    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private Tranzactie mapRow(ResultSet rs) throws SQLException {
        java.sql.Timestamp ts = rs.getTimestamp("data_ora");
        java.time.LocalDateTime dataOra;

        if (ts != null) {
            dataOra = ts.toLocalDateTime();
        } else {
            dataOra = java.time.LocalDateTime.now();
        }

        return new Tranzactie(
            rs.getString("id_tranzactie"),
            rs.getString("iban_sursa"),
            rs.getString("iban_destinatie"),
            rs.getDouble("suma"),
            Tranzactie.TipTranzactie.valueOf(rs.getString("tip_tranzactie")),
            dataOra,
            rs.getString("descriere")
        );
    }

    @Override
    public void save(Tranzactie entity) throws SQLException {
        String sql = "INSERT INTO tranzactie (id_tranzactie, iban_sursa, iban_destinatie, suma, tip_tranzactie, data_ora, descriere) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getIdTranzactie());
            pstmt.setString(2, entity.getIbanSursa());
            pstmt.setString(3, entity.getIbanDestinatie());
            pstmt.setDouble(4, entity.getSuma());
            pstmt.setString(5, entity.getTip().name());
            pstmt.setTimestamp(6, Timestamp.valueOf(entity.getDataOra()));
            pstmt.setString(7, entity.getDescriere());
            pstmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }

    @Override
    public Optional<Tranzactie> findById(String id) throws SQLException {
        String sql = "SELECT * FROM tranzactie WHERE id_tranzactie = ?";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Tranzactie> findAll() throws SQLException {
        List<Tranzactie> list = new ArrayList<>();
        String sql = "SELECT * FROM tranzactie";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
        return list;
    }

    @Override
    public void update(Tranzactie entity) throws SQLException {
        String sql = "UPDATE tranzactie SET iban_sursa=?, iban_destinatie=?, suma=?, tip_tranzactie=?, data_ora=?, descriere=? WHERE id_tranzactie=?";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getIbanSursa());
            pstmt.setString(2, entity.getIbanDestinatie());
            pstmt.setDouble(3, entity.getSuma());
            pstmt.setString(4, entity.getTip().name());
            pstmt.setTimestamp(5, Timestamp.valueOf(entity.getDataOra()));
            pstmt.setString(6, entity.getDescriere());
            pstmt.setString(7, entity.getIdTranzactie());
            pstmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM tranzactie WHERE id_tranzactie = ?";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }
}
