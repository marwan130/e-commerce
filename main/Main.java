package main;

import products.*;
import cart.Cart;
import customer.Customer;
import checkout.CheckoutService;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // test case 1 
        System.out.println("\ntest case 1, normal check out");
        try {
            Cheese cheese = new Cheese("Cheese", 100, 10, 0.2, LocalDate.now().plusDays(2));
            TV tv = new TV("TV", 500, 6, 2.5);
            MobileScratchCard scratchCard = new MobileScratchCard("Scratch Card", 50, 10);
            Cart cart = new Cart();
            Customer customer = new Customer("Marwan", 1000);
            cart.add(cheese, 2);
            cart.add(tv, 3);
            cart.add(scratchCard, 1);
            CheckoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // test case 2 
        System.out.println("\ntest case 2, out of stock");
        try {
            Cheese cheese = new Cheese("Cheese", 100, 10, 0.2, LocalDate.now().plusDays(2));
            Cart cart = new Cart();
            Customer customer = new Customer("Marwan", 1000);
            cart.add(cheese, 20);
            CheckoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // test case 3 
        System.out.println("\ntest case 3, expired product");
        try {
            Cheese expiredCheese = new Cheese("Old Cheese", 100, 5, 0.2, LocalDate.now().minusDays(1));
            Cart cart = new Cart();
            Customer customer = new Customer("Marwan", 1000);
            cart.add(expiredCheese, 1);
            CheckoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // test case 4 
        System.out.println("\ntest case 4, insufficient balance");
        try {
            Cheese cheese = new Cheese("Cheese", 100, 10, 0.2, LocalDate.now().plusDays(2));
            Cart cart = new Cart();
            Customer poorCustomer = new Customer("Ali", 10);
            cart.add(cheese, 2); 
            CheckoutService.checkout(poorCustomer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // test case 5 
        System.out.println("\ntest case 5, empty cart");
        try {
            Cart cart = new Cart();
            Customer customer = new Customer("Marwan", 1000);
            CheckoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // test case 6 
        System.out.println("\ntest case 6, remove items");
        try {
            Cheese cheese = new Cheese("Cheese", 100, 10, 0.2, LocalDate.now().plusDays(2));
            Cart cart = new Cart();
            Customer customer = new Customer("Marwan", 1000);
            cart.add(cheese, 2);
            cart.remove(cheese, 1); 
            CheckoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // test case 7 
        System.out.println("\ntest case 7, remove non existent item");
        try {
            TV tv = new TV("TV", 500, 6, 2.5);
            Cart cart = new Cart();
            Customer customer = new Customer("Marwan", 1000);
            cart.remove(tv, 1); 
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // test case 8 
        System.out.println("\ntest case 8,add more of the same item to cart");
        try {
            Cheese cheese = new Cheese("Cheese", 100, 10, 0.2, LocalDate.now().plusDays(2));
            Cart cart = new Cart();
            Customer customer = new Customer("Marwan", 1000);
            cart.add(cheese, 1);
            cart.add(cheese, 2); 
            CheckoutService.checkout(customer, cart);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        // test case 9 
        System.out.println("\ntest case 9, clear cart");
        try {
            Cheese cheese = new Cheese("Cheese", 100, 10, 0.2, LocalDate.now().plusDays(2));
            Cart cart = new Cart();
            cart.add(cheese, 1);
            cart.clearCart();
            System.out.println(cart.isEmpty());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}