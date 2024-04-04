package app.entities;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Orderline>shoppingCartList;

    public ShoppingCart(){
        this.shoppingCartList= new ArrayList<>();
    }

    public List<Orderline> getShoppingCartList() {
        return shoppingCartList;
    }

    public void addOrderline(Orderline orderline){
        shoppingCartList.add(orderline);


    }

    public void removeOrderline(Orderline orderline){
        shoppingCartList.remove(orderline);
    }

    public double TotalPriceOrder(){
        double totalPrice=0.0;
        for(Orderline orderline:shoppingCartList)
            totalPrice+=orderline.getPrice();
        return totalPrice;
    }

}
