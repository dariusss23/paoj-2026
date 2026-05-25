package com.pao.project.banking.repository;

import com.pao.project.banking.model.Adresa;
import com.pao.project.banking.model.ClientFizic;
import com.pao.project.banking.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientFizicRepository implements Repository<ClientFizic, Integer> {

    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private ClientFizic mapRow(ResultSet rs) throws SQLException {
        String adresaStr = rs.getString("adresa");
        Adresa adresa = null;
        if (adresaStr != null) {
            String[] parts = adresaStr.split(",");
            if (parts.length >= 4) {
                adresa = new Adresa(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim());
            } else {
                adresa = new Adresa(adresaStr, "", "", "");
            }
        }
        
        java.sql.Date dbDate = rs.getDate("data_nasterii");
        LocalDate dataNasterii = null;
        if (dbDate != null) dataNasterii = dbDate.toLocalDate();

        return new ClientFizic(
            rs.getInt("id_client"),
            rs.getString("nume"),
            rs.getString("prenume"),
            rs.getString("cnp"),
            dataNasterii,
            rs.getString("telefon"),
            rs.getString("email"),
            adresa,
            rs.getBoolean("is_student")
        );
    }

    @Override
    public void save(ClientFizic entity) throws SQLException {
        String sql = "INSERT INTO client_fizic (nume, prenume, cnp, data_nasterii, telefon, email, adresa, is_student) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.getNume());
            pstmt.setString(2, entity.getPrenume());
            pstmt.setString(3, entity.getCnp());
            pstmt.setDate(4, Date.valueOf(entity.getDataNasterii()));
            pstmt.setString(5, entity.getTelefon());
            pstmt.setString(6, entity.getEmail());
            pstmt.setString(7, entity.getAdresa().toString());
            pstmt.setBoolean(8, entity.isStudent());
            pstmt.executeUpdate();
            
            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    entity.setIdClient(keys.getInt(1));
                }
            }
            
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<ClientFizic> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM client_fizic WHERE id_client = ?";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
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
    public List<ClientFizic> findAll() throws SQLException {
        List<ClientFizic> list = new ArrayList<>();
        String sql = "SELECT * FROM client_fizic ORDER BY id_client";
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
    public void update(ClientFizic entity) throws SQLException {
        String sql = "UPDATE client_fizic SET nume=?, prenume=?, cnp=?, data_nasterii=?, telefon=?, email=?, adresa=?, is_student=? WHERE id_client=?";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entity.getNume());
            pstmt.setString(2, entity.getPrenume());
            pstmt.setString(3, entity.getCnp());
            pstmt.setDate(4, Date.valueOf(entity.getDataNasterii()));
            pstmt.setString(5, entity.getTelefon());
            pstmt.setString(6, entity.getEmail());
            pstmt.setString(7, entity.getAdresa().toString());
            pstmt.setBoolean(8, entity.isStudent());
            pstmt.setInt(9, entity.getIdClient());
            pstmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM client_fizic WHERE id_client = ?";
        try (Connection conn = getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (IOException e) {
            throw new SQLException("Eroare la conexiune", e);
        }
    }
}
