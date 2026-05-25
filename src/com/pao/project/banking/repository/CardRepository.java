package com.pao.project.banking.repository;

import com.pao.project.banking.model.Card;
import com.pao.project.banking.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardRepository implements Repository<Card, String> {

    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    /* Mapeaza un rand din ResultSet intr-un obiect. */
    private Card mapRow(ResultSet rs) throws SQLException {
        Card c = new Card(
            rs.getString("numar_card"),
            rs.getString("iban_cont"),
            Card.TipCard.valueOf(rs.getString("tip_card")),
            rs.getString("data_expirare"),
            rs.getString("cvv")
        );
        c.setStare(Card.StareCard.valueOf(rs.getString("stare")));
        return c;
    }

    @Override
    public void save(Card entity) throws SQLException {
        String sql = "INSERT INTO card (numar_card, iban_cont, tip_card, stare, data_expirare, cvv) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getNumarCard());
            pstmt.setString(2, entity.getIbanCont());
            pstmt.setString(3, entity.getTipCard().name());
            pstmt.setString(4, entity.getStare().name());
            pstmt.setString(5, entity.getDataExpirare());
            pstmt.setString(6, entity.getCVV());
            pstmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }

    @Override
    public Optional<Card> findById(String id) throws SQLException {
        String sql = "SELECT * FROM card WHERE numar_card = ?";
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
    public List<Card> findAll() throws SQLException {
        List<Card> list = new ArrayList<>();
        String sql = "SELECT * FROM card";
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
    public void update(Card entity) throws SQLException {
        String sql = "UPDATE card SET iban_cont=?, tip_card=?, stare=?, data_expirare=?, cvv=? WHERE numar_card=?";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getIbanCont());
            pstmt.setString(2, entity.getTipCard().name());
            pstmt.setString(3, entity.getStare().name());
            pstmt.setString(4, entity.getDataExpirare());
            pstmt.setString(5, entity.getCVV());
            pstmt.setString(6, entity.getNumarCard());
            pstmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM card WHERE numar_card = ?";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }
}
