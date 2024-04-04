package app.controllers;

import app.entities.Orderline;
import app.entities.ShoppingCart;
import app.entities.User;
import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;


public class ShoppingCartController {


    public static void addRoutes(Javalin app, ConnectionPool connectionPool,ShoppingCart shoppingCart){
        app.post("/add-to-cart", ctx->addToCart(ctx, shoppingCart));
        app.get("/shoppingCart", ctx->shoppingCartOverview(ctx, shoppingCart));
        app.post("/orderConfirmation", ctx->orderConfirmation(ctx, shoppingCart));
        app.post("/returnToOrder", ctx->ctx.render("cupcake.html"));

    }

    private static void addToCart(Context ctx, ShoppingCart shoppingCart) {
        String cupcakeTop=ctx.formParam("cupcakeTop");
        String cupcakeBottom=ctx.formParam("cupcakeBottom");
        int amount=Integer.parseInt(ctx.formParam("amount"));
        double price=Double.parseDouble(ctx.formParam("price"));

        User currentUser=ctx.sessionAttribute("currentUser");

        Orderline orderline= new Orderline(cupcakeTop,cupcakeBottom,amount,price);

        shoppingCart.addOrderline(orderline);

    }

    private static void shoppingCartOverview(Context ctx, ShoppingCart shoppingCart) {

        List<Orderline>shoppingCartList=shoppingCart.getShoppingCartList();
        double totalPrice=shoppingCart.TotalPriceOrder();
        ctx.attribute("cart",shoppingCartList);
        ctx.attribute("TotalPrice",totalPrice);
        ctx.render("shoppingCart.html");

    }

    private static void orderConfirmation(Context ctx, ShoppingCart shoppingCart) {
        List<Orderline>shoppingCartList=shoppingCart.getShoppingCartList();
        ctx.attribute("cart",shoppingCartList);
        ctx.render("orderConfirmation.html");
    }


}
