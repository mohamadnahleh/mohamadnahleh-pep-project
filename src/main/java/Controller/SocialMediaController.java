package Controller;

import Model.*;
import Service.*;
import io.javalin.*;
import io.javalin.http.Context;

public class SocialMediaController {
    private AccountService accountService = new AccountService();
    private MessageService messageService = new MessageService();

    public Javalin startAPI() {
        Javalin app = Javalin.create();

        app.post("/register", this::handleRegister);
        app.post("/login", this::handleLogin);
        app.post("/messages", this::handleCreateMessage);
        app.get("/messages", this::handleGetAllMessages);
        app.get("/messages/{id}", this::handleGetMessageById);
        app.delete("/messages/{id}", this::handleDeleteMessage);
        app.patch("/messages/{id}", this::handleUpdateMessage);
        app.get("/accounts/{id}/messages", this::handleGetMessagesByUser);

        return app;
    }

    private void handleRegister(Context ctx) {
        try {
            Account account = ctx.bodyAsClass(Account.class);
            Account created = accountService.register(account);
            if (created != null) {
                ctx.json(created);
            } else {
                ctx.status(400);
            }
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    private void handleLogin(Context ctx) {
        try {
            Account account = ctx.bodyAsClass(Account.class);
            Account loggedIn = accountService.login(account);
            ctx.json(loggedIn);
        } catch (Exception e) {
            ctx.status(401);
        }
    }

    private void handleCreateMessage(Context ctx) {
        try {
            Message message = ctx.bodyAsClass(Message.class);
            Message created = messageService.createMessage(message);
            ctx.json(created);
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    private void handleGetAllMessages(Context ctx) {
        try {
            ctx.json(messageService.getAllMessages());
        } catch (Exception e) {
            ctx.status(500);
        }
    }

    private void handleGetMessageById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Message msg = messageService.getMessageById(id);
            if (msg != null) ctx.json(msg);
            else ctx.json("");
        } catch (Exception e) {
            ctx.status(500);
        }
    }

    private void handleDeleteMessage(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Message deleted = messageService.deleteMessage(id);
            if (deleted != null) ctx.json(deleted);
            else ctx.json("");
        } catch (Exception e) {
            ctx.status(500);
        }
    }

    private void handleUpdateMessage(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Message msg = ctx.bodyAsClass(Message.class);
            Message updated = messageService.updateMessage(id, msg.getMessage_text());
            if (updated != null) ctx.json(updated);
            else ctx.status(400);
        } catch (Exception e) {
            ctx.status(400);
        }
    }

    private void handleGetMessagesByUser(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            ctx.json(messageService.getMessagesByUserId(id));
        } catch (Exception e) {
            ctx.status(500);
        }
    }
}
