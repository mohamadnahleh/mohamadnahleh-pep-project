package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    private MessageDAO dao;

    public MessageService() {
        this.dao = new MessageDAO();
    }

    public Message createMessage(Message message) throws Exception {
        if (message.getMessage_text() == null || message.getMessage_text().isBlank() || message.getMessage_text().length() > 255) {
            throw new Exception("Invalid message text");
        }
        return dao.createMessage(message);
    }

    public List<Message> getAllMessages() throws Exception {
        return dao.getAllMessages();
    }

    public Message getMessageById(int id) throws Exception {
        return dao.getMessageById(id);
    }

    public Message deleteMessage(int id) throws Exception {
        return dao.deleteMessage(id);
    }

    public Message updateMessage(int id, String newText) throws Exception {
        if (newText == null || newText.isBlank() || newText.length() > 255) {
            throw new Exception("Invalid message text");
        }
        return dao.updateMessageText(id, newText);
    }

    public List<Message> getMessagesByUserId(int accountId) throws Exception {
        return dao.getMessagesByAccountId(accountId);
    }
}