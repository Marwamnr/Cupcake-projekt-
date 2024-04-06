package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class UserController {

    public static void addRoutes(Javalin app, ConnectionPool connectionpool) {
        app.get("/", ctx -> index(ctx, connectionpool));
        app.post("/login", ctx -> login(ctx, connectionpool));
        app.get("logout", ctx->logout(ctx)); //jeg får lavet en logout
        app.get("createuser",ctx->ctx.render("createuser.html"));
        app.post("createuser",ctx->createUser(ctx,connectionpool));

    }

    private static void index(Context ctx, ConnectionPool connectionpool) {
        ctx.render("index.html");
    }

    private static void logout(Context ctx) {
        ctx.req().getSession().invalidate(); //sletter alle data,
        ctx.render("index.html"); //
    }

    private static void createUser(Context ctx,ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password1 = ctx.formParam("password1");
        String password2 = ctx.formParam("password2");

        if (password1.equals(password2)) {
            try {
                UserMapper.createuser(email, password1, connectionPool);
                ctx.attribute("message", "Du er hermed oprettet med email:" + email);
                ctx.render("index.html");
            } catch (DatabaseException e) {
                ctx.attribute("message", "Dit email findes allerede! Prøv igen eller login");
                ctx.render("createuser.html");
            }
        } else {
            ctx.attribute("message", "Dine to passwords matcher ikke! Prøv igen");
            ctx.render("createuser.html");
        }
    }

    public static void login(Context ctx, ConnectionPool connectionPool) {

        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.login(email, password, connectionPool);
            ctx.sessionAttribute("currentUser",user);

            ctx.render("Cupcake.html");

        } catch (DatabaseException e) {

            ctx.attribute("message", e.getMessage());


            ctx.render("index.html");
        }
    }
}