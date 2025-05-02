package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {
    public Account register(Account account) throws SQLException {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "INSERT INTO account (username, password) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, account.getUsername());
        ps.setString(2, account.getPassword());

        if (ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                account.setAccount_id(rs.getInt(1));
                return account;
            }
        }
        return null;
    }

    public Account login(String username, String password) throws SQLException {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Account(
                rs.getInt("account_id"),
                rs.getString("username"),
                rs.getString("password")
            );
        }
        return null;
    }
}
