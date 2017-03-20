package com.sap.icf.samples.shoppinglist.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="\"com.sap.icf.samples::ShoppingList.ShoppingListHeaderSeq\"", initialValue=1, allocationSize=10)
@Table(name="\"com.sap.icf.samples::ShoppingList.ShoppingListHeader\"")
public class ShoppingListHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "\"ShoppingListId\"")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "\"com.sap.icf.samples::ShoppingList.ShoppingListHeaderSeq\"")
    private Long shoppingListId;

    @Column(name = "\"ShoppingListName\"")
    private String shoppingListName;

    @Column(name = "\"ShoppingListOwner\"")
    private String shoppingListOwner;

    @OneToMany(mappedBy = "shoppingListHeader", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ShoppingListItem> shoppingListItems;

    public ShoppingListHeader() {
        super();
        shoppingListItems = new ArrayList<ShoppingListItem>();
    }

    public Long getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(Long shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public String getShoppingListName() {
        return shoppingListName;
    }

    public void setShoppingListName(String shoppingListName) {
        this.shoppingListName = shoppingListName;
    }

    public String getShoppingListOwner() {
        return shoppingListOwner;
    }

    public void setShoppingListOwner(String shoppingListOwner) {
        this.shoppingListOwner = shoppingListOwner;
    }

    public List<ShoppingListItem> getShoppingListItems() {
        return shoppingListItems;
    }

    public void setShoppingListItems(List<ShoppingListItem> shoppingListItems) {
        this.shoppingListItems = shoppingListItems;
    }
}
