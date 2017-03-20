package com.sap.icf.samples.shoppinglist.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.i18n.LocaleContextHolder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sap.icd.odatav2.spring.annotations.ModelProperty;
import com.sap.icd.odatav2.spring.listeners.DispatcherEntityListener;

@Entity
@SequenceGenerator(name = "\"com.sap.icf.samples::ShoppingList.ProductSeq\"", initialValue = 1, allocationSize = 10)
@Table(name = "\"com.sap.icf.samples::ShoppingList.Product\"")
@EntityListeners(DispatcherEntityListener.class)
public class Product implements Serializable {


	
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "\"ProductId\"")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "\"com.sap.icf.samples::ShoppingList.ProductSeq\"")
    private Long productId;

    @Column(name = "\"ProductCategoryId\"")
    private Integer productCategoryId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "\"ProductId\"")
    private List<ProductText> productTexts;

    @Transient
    @ModelProperty("computed")
    private String productTextLocalized;

    @Transient
    @ModelProperty("computed")
    private String productDescriptionLocalized;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ShoppingListItem> shoppingListItems;

    public Product() {
        super();
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public List<ShoppingListItem> getShoppingListItems() {
        return shoppingListItems;
    }

    public List<ProductText> getProductTexts() {
        return productTexts;
    }

    public void setProductTexts(List<ProductText> productTexts) {
        this.productTexts = productTexts;
    }

    public String getProductTextLocalized() {
        for (ProductText productText : productTexts) {
            if (productText.getLanguage().equals(LocaleContextHolder.getLocale().getLanguage())) {
                productTextLocalized = productText.getProductName();
            }
        }

        return productTextLocalized;
    }

    public void setProductTextLocalized(String productTextLocalized) {
        this.productTextLocalized = productTextLocalized;
    }


    public String getProductDescriptionLocalized() {
        for (ProductText productText : productTexts) {
            if (productText.getLanguage().equals(LocaleContextHolder.getLocale().getLanguage())) {
                productDescriptionLocalized = productText.getProductDescription();
            }
        }
        return productDescriptionLocalized;
    }

    public void setProductDescriptionLocalized(String productDescriptionLocalized) {
        this.productDescriptionLocalized = productDescriptionLocalized;
    }

}
