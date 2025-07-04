package products;

import interfaces.Expirable;
import interfaces.Shippable;
import java.time.LocalDate;

public class Biscuits extends Product implements Shippable, Expirable {
    private LocalDate expiryDate;
    private double weight;

    public Biscuits(String name, double price, int quantity, double weight, LocalDate expiryDate) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
        this.weight = weight;
    }

    @Override 
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public double getWeight() {
        return weight;
    }
}