package app.controllers;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.BalanceMapper;
import io.javalin.http.Context;
import io.javalin.Javalin;


public class BalanceController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        // Define route to handle form submission
        app.post("/addBalance", ctx -> addBalance(ctx, connectionPool));
        app.get("/addBalance", ctx -> ctx.render("balance.html"));
    }

    public static void addBalance(Context ctx, ConnectionPool connectionPool) {
        // Extract email and balance from the request
        String email = ctx.formParam("email");
        int balance = 0; // Initialize balance variable with a default value

        try {
            // Parse balance from form parameter
            balance = Integer.parseInt(ctx.formParam("balance"));

            // Call the method to add balance to the user's account
            BalanceMapper.addBalance(email, balance, connectionPool);

            // Set success message
            ctx.result("Balance added successfully");
        } catch (NumberFormatException e) {
            // Handle the case when the balance parameter cannot be parsed as an integer
            ctx.result("Invalid balance value");
            ctx.status(400); // Set status to indicate a bad request
        } catch (DatabaseException e) {
            // Handle database exception
            ctx.result("Failed to add balance: " + e.getMessage());
            ctx.status(500); // Set status to indicate an internal server error
        }
    }
}