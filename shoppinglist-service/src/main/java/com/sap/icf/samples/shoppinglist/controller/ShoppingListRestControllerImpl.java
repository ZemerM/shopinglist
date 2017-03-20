package com.sap.icf.samples.shoppinglist.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.sap.icf.samples.shoppinglist.api.ShoppingListsApi;
import com.sap.icf.samples.shoppinglist.dto.ShoppingList;
import com.sap.icf.samples.shoppinglist.service.ShoppingListService;

@RestController
public class ShoppingListRestControllerImpl implements ShoppingListsApi {

    private static final String PAGE_SIZE_DEFAULT = "20";
    private ShoppingListService shoppingListService;
    // TODO: Autowire Logger
    private Logger logger = LoggerFactory.getLogger(ch.qos.logback.classic.Logger.class);


    @Autowired
    public ShoppingListRestControllerImpl(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @Override
    public ResponseEntity<List<ShoppingList>> shoppingListsGet(
            @RequestParam(value = "pageId", required = false, defaultValue = "0") Integer pageId,
            @RequestParam(value = "pageSize", required = false, defaultValue = PAGE_SIZE_DEFAULT) Integer pageSize) {
        return new ResponseEntity<>(shoppingListService.getShoppingLists(pageId, pageSize), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> shoppingListsPost(@RequestBody ShoppingList shoppingList) {
        Long headerId = shoppingListService.postShoppingList(shoppingList);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(headerId).toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ShoppingList> shoppingListsIdGet(@PathVariable("id") Long id) {
        ShoppingList shoppingList = shoppingListService.getShoppingList(id);
        return new ResponseEntity<>(shoppingList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> shoppingListsIdPut(@PathVariable("id") Long id,
            @RequestBody ShoppingList shoppingList) {
        shoppingListService.putShoppingList(id, shoppingList);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri());
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Void> shoppingListsIdDelete(@PathVariable("id") Long id) {
        shoppingListService.deleteShoppingList(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
