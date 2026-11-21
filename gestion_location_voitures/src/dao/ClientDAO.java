package dao;

import model.Client;
import service.GestionDemandesLocations;
import service.GestionVoitures;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    private final GestionVoitures gestionVoitures;
    private final GestionDemandesLocations gestionDemandesLocations;

    public ClientDAO(GestionVoitures gv, GestionDemandesLocations gdl) {
        this.gestionVoitures = gv;
        this.gestionDemandesLocations = gdl;
    }

    public void insertClientPart(int idUser, String nom, String prenom) throws SQLException {
        String sql = "INSERT INTO client (idClient, nom, prenom) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ps.setString(2, nom);
            ps.setString(3, prenom);
            ps.executeUpdate();
        }
    }

    public Client findById(int idUser) throws SQLException {
        String sql = "SELECT u.idUser, u.username, u.password, u.role, c.nom, c.prenom " +
                     "FROM user u JOIN client c ON u.idUser = c.idClient " +
                     "WHERE u.idUser = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Client> findAll() throws SQLException {
        String sql = "SELECT u.idUser, u.username, u.password, u.role, c.nom, c.prenom " +
                     "FROM user u JOIN client c ON u.idUser = c.idClient";
        List<Client> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public void delete(int idUser) throws SQLException {
        String sql = "DELETE FROM client WHERE idClient = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ps.executeUpdate();
        }
    }

    private Client map(ResultSet rs) throws SQLException {
        int id = rs.getInt("idUser");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String role = rs.getString("role");
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");

        Client c = new Client(id, username, password, role, nom, prenom, gestionVoitures, gestionDemandesLocations);
        return c;
    }
}

