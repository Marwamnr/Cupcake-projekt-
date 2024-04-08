package app.entities;

public class ShoppingCartLine {
    private Bund bund;
    private Topping topping;

    private int amount;

    public ShoppingCartLine(Bund bund, Topping topping, int amount) {
        this.bund = bund;
        this.topping = topping;
        this.amount = amount;
    }

    public Bund getBund() {
        return bund;
    }

    public Topping getTopping() {
        return topping;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice()
    {
        return amount * (topping.getToppingPrice() + bund.getBundPrice());
    }
}
