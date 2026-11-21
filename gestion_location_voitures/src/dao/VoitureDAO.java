package dao;

import model.Voiture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoitureDAO {

    public void insert(Voiture v) throws SQLException {
        String sql = "INSERT INTO voiture (marque, isDispo, typeVoiture, prixJour) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, v.getMarque());
            ps.setBoolean(2, v.getIsDispo());
            ps.setString(3, v.getTypeVoiture());
            ps.setDouble(4, v.getPrixJour());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    v.setIdVoiture(rs.getInt(1));
                }
            }
        }
    }

    public Voiture findById(int id) throws SQLException {
        String sql = "SELECT idVoiture, marque, isDispo, typeVoiture, prixJour FROM voiture WHERE idVoiture = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Voiture> findAll() throws SQLException {
        String sql = "SELECT idVoiture, marque, isDispo, typeVoiture, prixJour FROM voiture";
        List<Voiture> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public List<Voiture> findAllDisponibles() throws SQLException {
        String sql = "SELECT idVoiture, marque, isDispo, typeVoiture, prixJour FROM voiture WHERE isDispo = 1";
        List<Voiture> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public void update(Voiture v) throws SQLException {
        String sql = "UPDATE voiture SET marque = ?, isDispo = ?, typeVoiture = ?, prixJour = ? WHERE idVoiture = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getMarque());
            ps.setBoolean(2, v.getIsDispo());
            ps.setString(3, v.getTypeVoiture());
            ps.setDouble(4, v.getPrixJour());
            ps.setInt(5, v.getIdVoiture());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM voiture WHERE idVoiture = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Voiture map(ResultSet rs) throws SQLException {
        int id = rs.getInt("idVoiture");
        String marque = rs.getString("marque");
        boolean isDispo = rs.getBoolean("isDispo");
        String type = rs.getString("typeVoiture");
        double prixJour = rs.getDouble("prixJour");
        return new Voiture(id, marque, isDispo, type, prixJour);
    }
}

