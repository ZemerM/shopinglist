package com.sap.icf.samples.shoppinglist.service;

import com.sap.icf.samples.shoppinglist.model.Product;
import com.sap.icf.samples.shoppinglist.model.ProductText;
import com.sap.icf.samples.shoppinglist.model.ShoppingListHeader;
import com.sap.icf.samples.shoppinglist.model.ShoppingListItem;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * Maps DTO and entity classes
 *
 * This Mapper is the bridge between the entity classes and the DTO classes
 * (Data Transfer Object, which are part of the api).
 *
 * Entity classes and DTO classes don't know each other. The Mapper is the only
 * source, which handles the transformation between DTOs and entities and vice
 * versa.
 *
 * This allows to exchange a entity implementation (or a DTO implementation)
 * just by providing a new Mapper class.
 *
 */
@Component
public class Mapper {

    // --------------------------------------------------------------------------------------------------------
    // Product 
    // --------------------------------------------------------------------------------------------------------
    public com.sap.icf.samples.shoppinglist.dto.Product toDto(Product product) {
        com.sap.icf.samples.shoppinglist.dto.Product productDto = new com.sap.icf.samples.shoppinglist.dto.Product();
        productDto.setProductId(product.getProductId());
        productDto.setProductCategoryId(product.getProductCategoryId());
        productDto.setProductNameLocalized(product.getProductTextLocalized());
        productDto.setProductDescriptionLocalized(product.getProductDescriptionLocalized());
        List<com.sap.icf.samples.shoppinglist.dto.ProductText> dtoTexts = new ArrayList<>();
        product.getProductTexts().stream().forEach(e -> {
            dtoTexts.add(toDto(e));
        });
        productDto.setProductTexts(dtoTexts);
        return productDto;
    }

    public Product toEntity(com.sap.icf.samples.shoppinglist.dto.Product productDto) {
        Product product = new Product();
        product.setProductId(productDto.getProductId());
        product.setProductCategoryId(productDto.getProductCategoryId());
        List<ProductText> texts = new ArrayList<>();
        productDto.getProductTexts().stream().forEach(e -> {
            ProductText productText = toEntity(e);
            productText.setProductId(productDto.getProductId());
            texts.add(productText);
        });
        product.setProductTexts(texts);
        return product;
    }

    // --------------------------------------------------------------------------------------------------------
    // ProductText 
    // --------------------------------------------------------------------------------------------------------
    public com.sap.icf.samples.shoppinglist.dto.ProductText toDto(ProductText productText) {
        com.sap.icf.samples.shoppinglist.dto.ProductText productTextDto = new com.sap.icf.samples.shoppinglist.dto.ProductText();
        productTextDto.setLanguage(productText.getLanguage());
        productTextDto.setProductDescription(productText.getProductDescription());
        productTextDto.setProductName(productText.getProductName());
        return productTextDto;
    }

    public ProductText toEntity(com.sap.icf.samples.shoppinglist.dto.ProductText productTextDto) {
        ProductText productText = new ProductText();
        productText.setLanguage(productTextDto.getLanguage());
        productText.setProductDescription(productTextDto.getProductDescription());
        productText.setProductName(productTextDto.getProductName());
        return productText;
    }

    // --------------------------------------------------------------------------------------------------------
    // ShoppingList
    // ShoppingListHeader
    // --------------------------------------------------------------------------------------------------------
    public com.sap.icf.samples.shoppinglist.dto.ShoppingList toDto(ShoppingListHeader header) {
        com.sap.icf.samples.shoppinglist.dto.ShoppingList listDto = new com.sap.icf.samples.shoppinglist.dto.ShoppingList();
        listDto.setShoppingListId(header.getShoppingListId());
        listDto.setShoppingListName(header.getShoppingListName());
        listDto.setShoppingListOwner(header.getShoppingListOwner());
        listDto.setShoppingListItems(new ArrayList<>());
        for (ShoppingListItem item : header.getShoppingListItems()) {
            listDto.getShoppingListItems().add(toDto(item));
        }
        return listDto;
    }

    public ShoppingListHeader toEntity(com.sap.icf.samples.shoppinglist.dto.ShoppingList listDto) {
        ShoppingListHeader header = new ShoppingListHeader();
        header.setShoppingListId(listDto.getShoppingListId());
        header.setShoppingListName(listDto.getShoppingListName());
        header.setShoppingListOwner(listDto.getShoppingListOwner());
        for (com.sap.icf.samples.shoppinglist.dto.ShoppingListItem itemDto : listDto.getShoppingListItems()) {
            ShoppingListItem item = toEntity(itemDto);
            item.setShoppingListHeader(header);
            header.getShoppingListItems().add(item);
        }

        return header;
    }

    // --------------------------------------------------------------------------------------------------------
    // ShoppingListItem 
    // --------------------------------------------------------------------------------------------------------
    public com.sap.icf.samples.shoppinglist.dto.ShoppingListItem toDto(ShoppingListItem item) {
        com.sap.icf.samples.shoppinglist.dto.ShoppingListItem dto = new com.sap.icf.samples.shoppinglist.dto.ShoppingListItem();
        dto.setProductId(item.getProductId());
        dto.setQuantity(item.getQuantity());
        dto.setShoppingListId(item.getShoppingListHeader().getShoppingListId());
        dto.setShoppingListItemId(item.getShoppingListItemId());
        dto.setStatus(item.getStatus());
        dto.setUnitOfMeasure(item.getUnitOfMeasure());
        return dto;
    }

    public ShoppingListItem toEntity(com.sap.icf.samples.shoppinglist.dto.ShoppingListItem itemDto) {
        ShoppingListItem item = new ShoppingListItem();
        item.setProductId(itemDto.getProductId());
        item.setQuantity(itemDto.getQuantity());
        item.setShoppingListItemId(itemDto.getShoppingListItemId());
        item.setStatus(itemDto.getStatus());
        item.setUnitOfMeasure(itemDto.getUnitOfMeasure());
        return item;
    }

}
