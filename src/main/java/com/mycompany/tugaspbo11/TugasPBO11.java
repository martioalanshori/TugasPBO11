package com.mycompany.tugaspbo11;

import java.util.List;
import java.util.ArrayList;

// Interface for Payment Methods
interface PaymentMethod {
    boolean processPayment(double amount);
}

// Abstract class for Items
abstract class Item {
    protected String name;
    protected double price;
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public abstract double calculateTotalPrice();
}

public class TugasPBO11 {
    // Non-static Inner Class
    public class CashRegister {
        private double totalSales;
        public void recordSale(double amount) {
            totalSales += amount;
        }
        public double getTotalSales() {
            return totalSales;
        }
    }

    // Static Inner Class
    public static class ReceiptPrinter {
        public static void printReceipt(List<Item> items, double total) {
            System.out.println("--- Struk ---");
            for (Item item : items) {
                System.out.printf("%s: Rp%d\n", item.name, (int)item.price);
            }
            System.out.printf("Total: Rp%d\n", (int)total);
        }
    }

    public void processSale() {
        // Local Inner Class
        class DiscountItem extends Item {
            private double discountRate;
            public DiscountItem(String name, double price, double discountRate) {
                super(name, price);
                this.discountRate = discountRate;
            }
            @Override
            public double calculateTotalPrice() {
                return price * (1 - discountRate);
            }
        }

        // List to store items
        List<Item> items = new ArrayList<>();

        // Adding items using anonymous class
        items.add(new Item("Roti", 6000) {
            @Override
            public double calculateTotalPrice() {
                return price;
            }
        });
        items.add(new DiscountItem("Susu", 25000, 0.1));

        // Calculate total
        double total = items.stream()
                .mapToDouble(Item::calculateTotalPrice)
                .sum();

        // Payment Method (Anonymous Inner Class)
        PaymentMethod cashPayment = new PaymentMethod() {
            @Override
            public boolean processPayment(double amount) {
                System.out.println("Pembayaran tunai diproses: Rp" + (int)amount);
                return true;
            }
        };

        // Process payment
        if (cashPayment.processPayment(total)) {
            // Create and use Non-static Inner Class
            CashRegister register = new CashRegister();
            register.recordSale(total);

            // Use Static Inner Class to print receipt
            ReceiptPrinter.printReceipt(items, total);
        }
    }

    public static void main(String[] args) {
        TugasPBO11 system = new TugasPBO11();
        system.processSale();
    }
}