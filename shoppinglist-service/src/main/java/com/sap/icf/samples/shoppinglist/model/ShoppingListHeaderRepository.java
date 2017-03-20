package com.sap.icf.samples.shoppinglist.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShoppingListHeaderRepository extends JpaRepository<ShoppingListHeader, Long> {
	List<ShoppingListHeader> findByShoppingListName(String shoppingListName);

	List<ShoppingListHeader> findByShoppingListOwner(String shoppingListOwner);
}
