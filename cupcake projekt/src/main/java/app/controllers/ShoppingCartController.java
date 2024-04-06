package app.controllers;

import app.entities.Orderline;
import app.entities.ShoppingCart;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderlineMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;
import java.util.ArrayList;


public class ShoppingCartController {
    public static void addRoutes(Javalin app, ConnectionPool connectionPool,ShoppingCart shoppingCart){
        app.post("/add-to-cart", ctx->addToCart(ctx, shoppingCart));

        //app.get("/shoppingCart", ctx -> ctx.render("shoppingCart.html"));
        app.get("/shoppingCart", ctx->shoppingCartOverview(ctx, shoppingCart));

        //app.post("/orderConfirmation", ctx -> addorderline(ctx, connectionPool));

        app.post("/orderConfirmation", ctx->orderConfirmation(ctx, shoppingCart));
        app.post("/returnToOrder", ctx->ctx.render("Cupcake.html"));
    }

    private static void addToCart(Context ctx, ShoppingCart shoppingCart) {
        int price=Integer.parseInt(ctx.formParam("price"));
        int orderlineId = Integer.parseInt(ctx.formParam("orderlineid"));
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        int bottomId = Integer.parseInt(ctx.formParam("bottomId"));
        int toppingId = Integer.parseInt(ctx.formParam("toppingId"));
        int amount=Integer.parseInt(ctx.formParam("amount"));

        User currentUser=ctx.sessionAttribute("currentUser");

        Orderline orderline= new Orderline(price,orderlineId,orderId,bottomId,toppingId,amount);

        shoppingCart.addOrderline(orderline);
    }

    private static void shoppingCartOverview(Context ctx, ShoppingCart shoppingCart) {

        List<Orderline>shoppingCartList=shoppingCart.getShoppingCartList();
        double totalPrice = shoppingCart.totalPriceOrder();
        ctx.attribute("cart",shoppingCartList);
        ctx.attribute("TotalPrice",totalPrice);
        ctx.render("shoppingCart.html");
    }

    private static void orderConfirmation(Context ctx, ShoppingCart shoppingCart) {
        List<Orderline>shoppingCartList=shoppingCart.getShoppingCartList();
        ctx.attribute("cart",shoppingCartList);
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