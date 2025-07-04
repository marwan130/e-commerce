package customer;

import cart.Cart;
import cart.CartItem;
import exceptions.EmptyCartException;
import exceptions.ExpiredProductException;
import exceptions.InsufficientBalanceException;
import exceptions.OutOfStockException;
import interfaces.Expirable;
import interfaces.Shippable;
import products.Product;
import shipping.ShippingService;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deductBalance(double amount) {
        if(amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        balance -= amount;
    }

    public void checkout(Cart cart) {
        if(cart.isEmpty()) {
            throw new EmptyCartException("Cart is empty");
        }

        double subtotal = cart.calculateSubtotal();
        List<CartItem> items = cart.getItems();
        List<CartItem> shippableItems = new ArrayList<>();

        for(CartItem item : items) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();

            if(product instanceof Expirable expirable && expirable.isExpired()) {
                throw new ExpiredProductException(product.getName() + " is expired");
            }

            if(quantity > product.getQuantity()) {
                throw new OutOfStockException(product.getName() + " is out of stock");
            }

            if(product instanceof Shippable) {
                shippableItems.add(item);
            }
        }
        
        ShippingService shippingService = new ShippingService();
        double shippingFee = shippingService.calculateShippingFee(shippableItems);
        double totalAmount = subtotal + shippingFee;

        if(balance < totalAmount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        for(CartItem item : items) {
            item.getProduct().updateQuantity(item.getQuantity());
        }

        deductBalance(totalAmount);

        shippingService.ship(shippableItems);
        printReceipt(items, subtotal, shippingFee, totalAmount);

        cart.clearCart();
    }

    private void printReceipt(List<CartItem> items, double subtotal, double shippingFee, double totalAmount) {
        System.out.println("** Checkout receipt **");
        for (CartItem item : items) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            double lineTotal = product.getPrice() * quantity;
            System.out.printf("%dx %s %.0f%n", quantity, product.getName(), lineTotal);
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f%n", subtotal);
        System.out.printf("Shipping %.0f%n", shippingFee);
        System.out.printf("Amount %.0f%n", totalAmount);
    }
    
}