package app.entities;

public class Orderline {
    private String cupcakeTop;
    private String cupcakeBottom;
    private int amount;
    private double price;

    public Orderline(String cupcakeTop, String cupcakeBottom, int amount, double price) {
        this.cupcakeTop = cupcakeTop;
        this.cupcakeBottom = cupcakeBottom;
        this.amount = amount;
        this.price = price;
    }

    public String getCupcakeTop() {
        return cupcakeTop;
    }

    public String getCupcakeBottom() {
        return cupcakeBottom;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public void setCupcakeTop(String cupcakeTop) {
        this.cupcakeTop = cupcakeTop;
    }

    public void setCupcakeBottom(String cupcakeBottom) {
        this.cupcakeBottom = cupcakeBottom;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
