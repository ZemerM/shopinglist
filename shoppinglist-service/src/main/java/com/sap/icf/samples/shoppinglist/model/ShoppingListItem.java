package com.sap.icf.samples.shoppinglist.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sap.icd.odatav2.spring.listeners.DispatcherEntityListener;

@Entity
@SequenceGenerator(name="\"com.sap.icf.samples::ShoppingList.ShoppingListItemSeq\"", initialValue=1, allocationSize=10)
@Table(name="\"com.sap.icf.samples::ShoppingList.ShoppingListItem\"")
@EntityListeners(DispatcherEntityListener.class)
public class ShoppingListItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "\"com.sap.icf.samples::ShoppingList.ShoppingListItemSeq\"")
    @Column(name = "\"ShoppingListItemId\"")
    private Long shoppingListItemId;

    @Column(name = "\"ShoppingListId\"", nullable = false, updatable = true, insertable = true)
    private Long shoppingListId;

    @ManyToOne
    @JoinColumn(name = "\"ShoppingListId\"", referencedColumnName = "\"ShoppingListId\"", nullable = false, updatable = false, insertable = false)
    @JsonBackReference
    private ShoppingListHeader shoppingListHeader;

    @Column(name = "\"ProductId\"", nullable = false)
    private Long productId;

    @Column(name = "\"Quantity\"")
    @Min(value = 1, message = "{shoppingListItem.invalidQuantity}") 
    private Double quantity;

    @Column(name = "\"UnitOfMeasure\"")
    private String unitOfMeasure;

    @Column(name = "\"Status\"")
    private Integer status;

    @ManyToOne
    @PrimaryKeyJoinColumn(name = "\"ProductId\"")
    private Product product;

    public ShoppingListItem() {
        super();
    }

    public Long getShoppingListItemId() {
        return shoppingListItemId;
    }

    public void setShoppingListItemId(Long shoppingListItemId) {
        this.shoppingListItemId = shoppingListItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ShoppingListHeader getShoppingListHeader() {
        return shoppingListHeader;
    }

    public void setShoppingListHeader(ShoppingListHeader shoppingListHeader) {
        this.shoppingListHeader = shoppingListHeader;
        this.shoppingListId = shoppingListHeader.getShoppingListId();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(Long shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

}
