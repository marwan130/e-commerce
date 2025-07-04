package products;

import exceptions.OutOfStockException;

public class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int amount) {
        if(amount > quantity) {
            throw new OutOfStockException("Not enough stock for " + name);
        }
        this.quantity -= amount;
    }
}