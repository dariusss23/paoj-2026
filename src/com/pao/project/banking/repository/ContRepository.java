package com.pao.project.banking.repository;

import com.pao.project.banking.model.Cont;
import com.pao.project.banking.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContRepository implements Repository<Cont, String> {

    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private Cont mapRow(ResultSet rs) throws SQLException {
        return new Cont(
            rs.getString("iban"),
            rs.getDouble("sold"),
            Cont.TipCont.valueOf(rs.getString("tip_cont")),
            rs.getString("moneda"),
            rs.getInt("id_proprietar")
        );
    }

    @Override
    public void save(Cont entity) throws SQLException {
        String sql = "INSERT INTO cont (iban, sold, tip_cont, moneda, id_proprietar) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getIban());
            pstmt.setDouble(2, entity.getSold());
            pstmt.setString(3, entity.getTipCont().name());
            pstmt.setString(4, entity.getMoneda());
            pstmt.setInt(5, entity.getIdProprietar());
            pstmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }

    @Override
    public Optional<Cont> findById(String id) throws SQLException {
        String sql = "SELECT * FROM cont WHERE iban = ?";
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
    public List<Cont> findAll() throws SQLException {
        List<Cont> list = new ArrayList<>();
        String sql = "SELECT * FROM cont";
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
    public void update(Cont entity) throws SQLException {
        String sql = "UPDATE cont SET sold=?, tip_cont=?, moneda=?, id_proprietar=? WHERE iban=?";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, entity.getSold());
            pstmt.setString(2, entity.getTipCont().name());
            pstmt.setString(3, entity.getMoneda());
            pstmt.setInt(4, entity.getIdProprietar());
            pstmt.setString(5, entity.getIban());
            pstmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }

    @Override
    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM cont WHERE iban = ?";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }
}
