package dao;

import model.Client;
import model.DemandeLocation;
import model.Voiture;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DemandeLocationDAO {

    private final ClientDAO clientDAO;
    private final VoitureDAO voitureDAO;

    public DemandeLocationDAO(ClientDAO clientDAO, VoitureDAO voitureDAO) {
        this.clientDAO = clientDAO;
        this.voitureDAO = voitureDAO;
    }

    public void insert(DemandeLocation d) throws SQLException {
        String sql = "INSERT INTO demandeLocation " +
                "(idClient, idVoiture, dateDebutLocation, dateFinLocation, typeLocation, prix, typePaiement, accepted) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, 0)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, d.getClient().getIdUser());
            ps.setInt(2, d.getVoiture().getIdVoiture());
            ps.setDate(3, Date.valueOf(d.getDateDebutLocation().toLocalDate()));
            ps.setDate(4, Date.valueOf(d.getDateFinLocation().toLocalDate()));
            ps.setString(5, d.getTypeLocation());
            ps.setDouble(6, d.getPrix());
            ps.setString(7, d.getTypePaiement());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    d.setIdDemande(rs.getInt(1));
                }
            }
        }
    }

    public DemandeLocation findById(int idDemande) throws SQLException {
        String sql = "SELECT * FROM demandeLocation WHERE idDemande = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDemande);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    // Only PENDING demandes
    public List<DemandeLocation> findAll() throws SQLException {
        String sql = "SELECT * FROM demandeLocation WHERE accepted = 0";
        List<DemandeLocation> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public void delete(int idDemande) throws SQLException {
        String sql = "DELETE FROM demandeLocation WHERE idDemande = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDemande);
            ps.executeUpdate();
        }
    }

    public void updatePrixEtPaiement(int idDemande, double prix, String typePaiement) throws SQLException {
        String sql = "UPDATE demandeLocation SET prix = ?, typePaiement = ? WHERE idDemande = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, prix);
            ps.setString(2, typePaiement);
            ps.setInt(3, idDemande);
            ps.executeUpdate();
        }
    }

    // Mark demande as accepted
    public void markAccepted(int idDemande) throws SQLException {
        String sql = "UPDATE demandeLocation SET accepted = 1 WHERE idDemande = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idDemande);
            ps.executeUpdate();
        }
    }

    private DemandeLocation map(ResultSet rs) throws SQLException {
        int idDemande = rs.getInt("idDemande");
        int idClient = rs.getInt("idClient");
        int idVoiture = rs.getInt("idVoiture");
        LocalDate dDebut = rs.getDate("dateDebutLocation").toLocalDate();
        LocalDate dFin = rs.getDate("dateFinLocation").toLocalDate();
        String typeLocation = rs.getString("typeLocation");
        double prix = rs.getDouble("prix");
        String typePaiement = rs.getString("typePaiement");

        Client client = clientDAO.findById(idClient);
        Voiture voiture = voitureDAO.findById(idVoiture);

        DemandeLocation demande = new DemandeLocation(
                idDemande,
                client,
                voiture,
                LocalDateTime.of(dDebut, java.time.LocalTime.MIN),
                LocalDateTime.of(dFin, java.time.LocalTime.MIN),
                typeLocation,
                typePaiement
        );
        demande.setPrix(prix);
        return demande;
    }
}



