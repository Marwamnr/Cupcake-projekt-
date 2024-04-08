package app.controllers;

import app.entities.Orderline;
import app.entities.ShoppingCart;
import app.entities.Topping;
import app.entities.Bund;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.BundMapper;
import app.persistence.ConnectionPool;
import app.persistence.OrderlineMapper;
import app.persistence.ToppingMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
import java.util.ArrayList;


public class ShoppingCartController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.post("/addtocart", ctx -> addToCart(ctx, connectionPool));

        //app.get("/shoppingCart", ctx -> ctx.render("shoppingCart.html"));
        app.get("/shoppingCart", ctx -> shoppingCartOverview(ctx));

        //app.post("/orderConfirmation", ctx -> addorderline(ctx, connectionPool));

        app.post("/orderConfirmation", ctx -> orderConfirmation(ctx));
        app.post("/returnToOrder", ctx -> ctx.render("cupcake.html"));
    }

    private static void addToCart(Context ctx, ConnectionPool connectionPool) {
        int bottomId = Integer.parseInt(ctx.formParam("bottomId"));
        int toppingId = Integer.parseInt(ctx.formParam("toppingId"));
        int amount = Integer.parseInt(ctx.formParam("quantity"));
        User currentUser = ctx.sessionAttribute("currentUser");
        try {
            Bund bund = BundMapper.getBundsById(bottomId, connectionPool);
            Topping topping = ToppingMapper.getToppingsById(toppingId, connectionPool);
            ShoppingCart shoppingCart = ctx.sessionAttribute("shoppingCart");
            if (shoppingCart == null) {
                shoppingCart = new ShoppingCart();
            }
            shoppingCart.add(topping, bund, amount);
            ctx.sessionAttribute("shoppingCart", shoppingCart);
            ctx.render("shoppingCart.html");
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void shoppingCartOverview(Context ctx) {

        ShoppingCart shoppingCart = ctx.sessionAttribute("shoppingCart");
        double totalPrice = shoppingCart.totalPriceShoppingCart();
        ctx.attribute("cart", shoppingCart);
        ctx.attribute("TotalPrice", totalPrice);
        ctx.render("shoppingCart.html");
    }

    private static void orderConfirmation(Context ctx) {
        ctx.render("orderConfirmation.html");
    }

    private static void addorderline(Context ctx, ConnectionPool connectionpool) {
        int price = Integer.parseInt(ctx.formParam("price"));
        int orderLineId = Integer.parseInt(ctx.formParam("orderLineId"));
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        int bottomId = Integer.parseInt(ctx.formParam("bottomId"));
        int toppingId = Integer.parseInt(ctx.formParam("toppingId"));
        int amount = Integer.parseInt(ctx.formParam("amount"));

        User user = ctx.sessionAttribute("currentUser");
        try {
            Orderline newOrderline = OrderlineMapper.createOrderline(orderLineId, orderId, bottomId, toppingId, price, amount, connectionpool);

            List<Orderline> orderlinesList = new ArrayList<>();
            orderlinesList.add(newOrderline);

            ctx.sessionAttribute("orderlineList", orderlinesList);

            ctx.render("orderConfirmation.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", "Noget gik galt. Prøv eventuelt igen!");
            ctx.render("orderConfirmation.html");
        }
    }
}