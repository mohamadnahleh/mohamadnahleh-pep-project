package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

public class MessageDAO {
    public Message createMessage(Message message) throws SQLException {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, message.getPosted_by());
        ps.setString(2, message.getMessage_text());
        ps.setLong(3, message.getTime_posted_epoch());

        if (ps.executeUpdate() > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                message.setMessage_id(rs.getInt(1));
                return message;
            }
        }
        return null;
    }

    public List<Message> getAllMessages() throws SQLException {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Message> messages = new ArrayList<>();
        while (rs.next()) {
            messages.add(new Message(
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
            ));
        }
        return messages;
    }

    public Message getMessageById(int id) throws SQLException {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message WHERE message_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Message(
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
            );
        }
        return null;
    }

    public Message deleteMessage(int id) throws SQLException {
        Message message = getMessageById(id);
        if (message == null) return null;

        Connection conn = ConnectionUtil.getConnection();
        String sql = "DELETE FROM message WHERE message_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();

        return message;
    }

    public Message updateMessageText(int id, String text) throws SQLException {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, text);
        ps.setInt(2, id);

        if (ps.executeUpdate() > 0) {
            return getMessageById(id);
        }
        return null;
    }

    public List<Message> getMessagesByAccountId(int accountId) throws SQLException {
        Connection conn = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message WHERE posted_by = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, accountId);
        ResultSet rs = ps.executeQuery();

        List<Message> messages = new ArrayList<>();
        while (rs.next()) {
            messages.add(new Message(
                rs.getInt("message_id"),
                rs.getInt("posted_by"),
                rs.getString("message_text"),
                rs.getLong("time_posted_epoch")
            ));
        }
        return messages;
    }
}