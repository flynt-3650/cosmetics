package com.adele.cosmetics.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Basket {

    private int entryBasket;
    private int applicationId;
    private int productId;
    private int productQuantity;
    private BigDecimal pricePerUnit;
    private BigDecimal totalAmount;

    public int getEntryBasket() {
        return entryBasket;
    }

    public void setEntryBasket(int entryBasket) {
        this.entryBasket = entryBasket;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return entryBasket == basket.entryBasket &&
                applicationId == basket.applicationId &&
                productId == basket.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entryBasket, applicationId, productId);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "entryBasket=" + entryBasket +
                ", applicationId=" + applicationId +
                ", productId=" + productId +
                ", productQuantity=" + productQuantity +
                ", pricePerUnit=" + pricePerUnit +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
