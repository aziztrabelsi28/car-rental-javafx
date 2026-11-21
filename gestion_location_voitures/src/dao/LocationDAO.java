package dao;

import model.DemandeLocation;
import model.Location;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationDAO {

    private final DemandeLocationDAO demandeLocationDAO;

    public LocationDAO(DemandeLocationDAO demandeLocationDAO) {
        this.demandeLocationDAO = demandeLocationDAO;
    }

    public void insert(Location l) throws SQLException {
        String sql = "INSERT INTO location (idDemande) VALUES (?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, l.getDemandeLocation().getIdDemande());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    l.setIdLocation(rs.getInt(1));
                }
            }
        }
    }

    public Location findById(int idLocation) throws SQLException {
        String sql = "SELECT idLocation, idDemande FROM location WHERE idLocation = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLocation);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Location> findAll() throws SQLException {
        String sql = "SELECT idLocation, idDemande FROM location";
        List<Location> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public void delete(int idLocation) throws SQLException {
        String sql = "DELETE FROM location WHERE idLocation = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLocation);
            ps.executeUpdate();
        }
    }

    private Location map(ResultSet rs) throws SQLException {
        int idLocation = rs.getInt("idLocation");
        int idDemande = rs.getInt("idDemande");
        DemandeLocation demande = demandeLocationDAO.findById(idDemande);
        Location l = new Location(idLocation, demande);
        return l;
    }
}

