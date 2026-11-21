package dao;

import model.Admin;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User findByUsernameAndPassword(String username, String password) throws SQLException {
        String sql = "SELECT idUser, username, password, role FROM user WHERE username = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapBasic(rs);
                }
            }
        }
        return null;
    }

    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT 1 FROM user WHERE username = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int insert(User u) throws SQLException {
        String sql = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getRole());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    u.setIdUser(id);
                    return id;
                }
            }
        }
        return -1;
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT idUser, username, password, role FROM user";
        List<User> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapBasic(rs));
            }
        }
        return list;
    }

    public void delete(int idUser) throws SQLException {
        String sql = "DELETE FROM user WHERE idUser = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idUser);
            ps.executeUpdate();
        }
    }

    private User mapBasic(ResultSet rs) throws SQLException {
        int id = rs.getInt("idUser");
        String username = rs.getString("username");
        String password = rs.getString("password");
        String role = rs.getString("role");

        if ("admin".equalsIgnoreCase(role)) {
            return new Admin(id, username, password, role);
        } else {
            return new User(id, username, password, role);
        }
    }
}


