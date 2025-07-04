package checkout;

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
import customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    public static void checkout(Customer customer, Cart cart) {
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

        if(customer.getBalance() < totalAmount) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        for(CartItem item : items) {
            item.getProduct().updateQuantity(item.getQuantity());
        }

        customer.deductBalance(totalAmount);

        shippingService.ship(shippableItems);
        printReceipt(items, subtotal, shippingFee, totalAmount);

        cart.clearCart();
    }

    public static void printReceipt(List<CartItem> items, double subtotal, double shippingFee, double totalAmount) {
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