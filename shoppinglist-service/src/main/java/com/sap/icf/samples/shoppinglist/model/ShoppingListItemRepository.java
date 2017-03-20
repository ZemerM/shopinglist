package com.sap.icf.samples.shoppinglist.model;



import org.springframework.data.jpa.repository.JpaRepository;


public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {
}
