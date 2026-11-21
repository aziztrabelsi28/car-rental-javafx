package dao;

import model.Facture;
import model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FactureDAO {

    private final LocationDAO locationDAO;

    public FactureDAO(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }

    public void insert(Facture f) throws SQLException {
        String sql = "INSERT INTO facture (idLocation, prixTotal, typePaiement) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, f.getLocation().getIdLocation());
            ps.setDouble(2, f.getPrix());
            ps.setString(3, f.getTypePaiement());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    f.setIdFacture(rs.getInt(1));
                }
            }
        }
    }

    public Facture findById(int idFacture) throws SQLException {
        String sql = "SELECT idFacture, idLocation, prixTotal, typePaiement FROM facture WHERE idFacture = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFacture);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Facture> findAll() throws SQLException {
        String sql = "SELECT idFacture, idLocation, prixTotal, typePaiement FROM facture";
        List<Facture> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public void delete(int idFacture) throws SQLException {
        String sql = "DELETE FROM facture WHERE idFacture = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFacture);
            ps.executeUpdate();
        }
    }

    public void updatePrix(int idFacture, double prix) throws SQLException {
        String sql = "UPDATE facture SET prixTotal = ? WHERE idFacture = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, prix);
            ps.setInt(2, idFacture);
            ps.executeUpdate();
        }
    }

    
    public List<Facture> findByClientId(int idClient) throws SQLException {
        String sql =
                "SELECT f.idFacture, f.idLocation, f.prixTotal, f.typePaiement " +
                "FROM facture f " +
                "JOIN location l ON f.idLocation = l.idLocation " +
                "JOIN demandeLocation d ON l.idDemande = d.idDemande " +
                "WHERE d.idClient = ?";
        List<Facture> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }

    private Facture map(ResultSet rs) throws SQLException {
        int idFacture = rs.getInt("idFacture");
        int idLocation = rs.getInt("idLocation");
        double prixTotal = rs.getDouble("prixTotal");
        String typePaiement = rs.getString("typePaiement");

        Location location = locationDAO.findById(idLocation);
        Facture f = new Facture(idFacture, location, prixTotal, typePaiement);
        f.setPrix(prixTotal);
        return f;
    }
}


