package app.controllers;

import app.entities.Orderline;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.http.Context;
import io.javalin.Javalin;
import java.util.List;
public class OrderController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        // Definer ruten til at vise ordrelisten
        app.get("/orderList", ctx -> showOrderList(ctx, connectionPool));
    }

    public static void showOrderList(Context ctx, ConnectionPool connectionPool) {
        try {
            // Hent ordrelisten fra databasen
            List<Orderline> orderList = OrderMapper.getOrders(connectionPool);

            // Tilføj ordrelisten til konteksten
            ctx.attribute("orderList", orderList);

            // Rendere ordrelistevisningen
            ctx.render("Order.html");
        } catch (Exception e) {
            // Håndter eventuelle undtagelser
            ctx.status(500).result("Fejl ved hentning af ordreliste: " + e.getMessage());
        }
    }
}
