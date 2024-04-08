package app.entities;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<ShoppingCartLine> shoppingCartList;

    public ShoppingCart(){
        this.shoppingCartList= new ArrayList<>();
    }

    public List<ShoppingCartLine> getShoppingCartList() {
        return shoppingCartList;
    }

    public void add(Topping topping, Bund bund, int amount){
        ShoppingCartLine shoppingCartLine = new ShoppingCartLine(bund, topping, amount);
        shoppingCartList.add(shoppingCartLine);
    }

    public void removeOrderline(Orderline orderline){
        shoppingCartList.remove(orderline);
    }

    public double totalPriceShoppingCart(){
        double sum = 0;
        for (ShoppingCartLine shoppingCartLine : shoppingCartList) {
           sum += shoppingCartLine.getAmount() * (shoppingCartLine.getBund().getBundPrice() + shoppingCartLine.getTopping().getToppingPrice());
        }
       return sum;
    }
}