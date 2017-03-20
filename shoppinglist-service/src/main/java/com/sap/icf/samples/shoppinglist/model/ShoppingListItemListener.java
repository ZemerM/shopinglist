package com.sap.icf.samples.shoppinglist.model;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import com.sap.icd.odatav2.spring.exceptions.UiRelevantRuntimeException;
import com.sap.icd.odatav2.spring.listeners.EntityListener;
import com.sap.icd.odatav2.spring.messages.Message;

@EntityListener(entities = {ShoppingListItem.class})
public class ShoppingListItemListener {

    @PreUpdate
    @PrePersist
    public void validateShoppingListItem(Object object) {

        ShoppingListItem shoppingListItem = (ShoppingListItem) object;

        if (shoppingListItem.getUnitOfMeasure().equals("EA") && shoppingListItem.getQuantity() % 1 != 0) {
            throw new UiRelevantRuntimeException(Message.builder().code("shoppingListItem.onlyWholeUnitsForUomEa")
                    .severity(Message.Severity.ERROR).build());
        }
    }

}
