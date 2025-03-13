package com.challenge.similar_product.domain;

public class ProductDetail {
    private String id;
    private String name;
    private double price;
    private boolean availability;

    private ProductDetail(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.price = builder.price;
        this.availability = builder.availability;
    }

    public static class Builder {
        private String id;
        private String name;
        private double price;
        private boolean availability;

        public Builder id(String id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder price(double price) { this.price = price; return this; }
        public Builder availability(boolean availability) { this.availability = availability; return this; }
        public ProductDetail build() { return new ProductDetail(this); }
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean isAvailability() { return availability; }
}