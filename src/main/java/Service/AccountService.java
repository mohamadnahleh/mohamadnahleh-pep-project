package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO dao;

    public AccountService() {
        this.dao = new AccountDAO();
    }

    public Account register(Account account) throws Exception {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new Exception("Username cannot be blank");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new Exception("Password must be at least 4 characters");
        }
        return dao.register(account);
    }

    public Account login(Account account) throws Exception {
        Account existing = dao.login(account.getUsername(), account.getPassword());
        if (existing == null) {
            throw new Exception("Invalid credentials");
        }
        return existing;
    }
}