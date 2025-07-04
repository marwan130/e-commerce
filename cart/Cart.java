package cart;

import exceptions.EmptyCartException;
import exceptions.OutOfStockException;
import products.Product;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> items;
    
    public Cart() {
        this.items = new ArrayList<>();
    }

    public void add(Product product, int quantity) {
        if(quantity > product.getQuantity()) {
            throw new OutOfStockException("Not enough Stock for " + product.getName());
        }

        for(int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if(item.getProduct().equals(product)) {
                int newQuantity = item.getQuantity() + quantity;

                if(newQuantity > product.getQuantity()) {
                    throw new OutOfStockException("Not enough stock for" + product.getName());
                }

                items.set(i, new CartItem(product, newQuantity));
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    public void remove(Product product, int quantity) {
        for(int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);

            if(item.getProduct().equals(product)) {
                int currentQuantity = item.getQuantity();

                if(quantity >= currentQuantity) {
                    items.remove(i);
                }
                else {
                    items.set(i, new CartItem(product, currentQuantity - quantity));
                }
                return;
            }
        }
        throw new IllegalArgumentException(product.getName() + " is not in the cart");
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double calculateSubtotal() {
        if(items.isEmpty()) {
            throw new EmptyCartException("Can not calculate subtotal as cart is empty");
        }

        double subtotal = 0;
        for(CartItem item : items) {
            subtotal += item.getLineTotal();
        }
        return subtotal;
    }

    public void clearCart() {
        items.clear();
    }
}
