package shipping;

import cart.CartItem;
import interfaces.Shippable;

import java.util.List;

public class ShippingService {

    private static final double rate_per_kg = 27.2;

    public void ship(List<CartItem> itemsToShip) {
        if(itemsToShip.isEmpty()) {
            return;
        }

        System.out.println("** Shipment notice **");
        double totalWeight = 0;

        for (CartItem item : itemsToShip) {
            if (item.getProduct() instanceof Shippable shippable) {
                int quantity = item.getQuantity();
                double weight = shippable.getWeight();
                totalWeight += weight * quantity;

                System.out.printf("%dx %s %dg%n", quantity, shippable.getName(), (int) (weight * 1000));
            }
        }

        System.out.printf("Total package weight %.1fkg%n", totalWeight);
    }

    public double calculateShippingFee(List<CartItem> items) {
        double totalWeight = 0;

        for (CartItem item : items) {
            if (item.getProduct() instanceof Shippable shippable) {
                totalWeight += shippable.getWeight() * item.getQuantity();
            }
        }

        return Math.round(totalWeight * rate_per_kg);
    }
}
