/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sap.icf.samples.shoppinglist.service;

import com.sap.icf.samples.shoppinglist.dto.ShoppingList;
import com.sap.icf.samples.shoppinglist.model.ShoppingListHeader;
import com.sap.icf.samples.shoppinglist.model.ShoppingListHeaderRepository;
import com.sap.icf.samples.shoppinglist.model.ShoppingListItem;
import com.sap.icf.samples.shoppinglist.model.ShoppingListItemRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author d025461
 */
@Service
public class ShoppingListService {

    @PersistenceContext
    private EntityManager em;
    private Logger logger = LoggerFactory.getLogger(ch.qos.logback.classic.Logger.class);
    private ShoppingListHeaderRepository headerRepo;
    private ShoppingListItemRepository itemRepo;
    private Mapper mapper;

    @Autowired
    public ShoppingListService(ShoppingListHeaderRepository headerRepo, ShoppingListItemRepository itemRepo, Mapper mapper) {
        this.headerRepo = headerRepo;
        this.itemRepo = itemRepo;
        this.mapper = mapper;
    }

    @Transactional
    public Long postShoppingList(ShoppingList shoppingList) {
        em.setProperty("LANGUAGE", LocaleContextHolder.getLocale().getLanguage());
        throwIfNotNull(shoppingList.getShoppingListId());
        ShoppingListHeader header = mapper.toEntity(shoppingList);
        List<ShoppingListItem> items = header.getShoppingListItems();
        header.setShoppingListItems(new ArrayList<ShoppingListItem>());
        headerRepo.save(header);
        items.stream().forEach(item -> item.setShoppingListId(header.getShoppingListId()));
        header.setShoppingListItems(items);
        headerRepo.save(header);
        return header.getShoppingListId();
    }

    @Transactional(readOnly = true)
    public List<ShoppingList> getShoppingLists(Integer pageId, Integer pageSize) {
        em.setProperty("LANGUAGE", LocaleContextHolder.getLocale().getLanguage());
        PageRequest request = new PageRequest(pageId, pageSize);
        Page<ShoppingListHeader> headers = headerRepo.findAll(request);
        List<com.sap.icf.samples.shoppinglist.dto.ShoppingList> shoppingListDtoList = new ArrayList<>();
        for (ShoppingListHeader header : headers) {
            shoppingListDtoList.add(mapper.toDto(header));
        }
        return shoppingListDtoList;
    }

    @Transactional(readOnly = true)
    public ShoppingList getShoppingList(Long id) {
        em.setProperty("LANGUAGE", LocaleContextHolder.getLocale().getLanguage());
        throwIfNotExists(id);
        ShoppingListHeader header = headerRepo.findOne(id);
        return mapper.toDto(header);
    }

    @Transactional
    public void putShoppingList(Long id, ShoppingList shoppingList) {
        em.setProperty("LANGUAGE", LocaleContextHolder.getLocale().getLanguage());
        throwIfNotExists(id);
        ShoppingListHeader header = mapper.toEntity(shoppingList);
        header.setShoppingListId(id);
        header.getShoppingListItems().stream().forEach(item -> item.setShoppingListId(id));
        headerRepo.save(header);
    }

    @Transactional
    public void deleteShoppingList(Long id) {
        em.setProperty("LANGUAGE", LocaleContextHolder.getLocale().getLanguage());
        headerRepo.delete(id);
    }

    private void throwIfNotExists(Long id) {
        if (id != null) {
            if (!headerRepo.exists(id)) {
                ShoppingListNotFoundException exception = new ShoppingListNotFoundException(id);
                logger.warn(exception.getMessage());
                throw exception;
            }
        }
    }

    private void throwIfExists(Long id) {
        if (id != null && headerRepo.exists(id)) {
            ShoppingListAlreadyExistsException exception = new ShoppingListAlreadyExistsException(id);
            logger.warn(exception.getMessage());
            throw exception;
        }
    }

    private void throwIfNotNull(Long id) {
        if (id != null && id != 0) {
            ShoppingListIdNotNullException exception = new ShoppingListIdNotNullException();
            logger.warn(exception.getMessage());
            throw exception;
        }
    }

    @SuppressWarnings("serial")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class ShoppingListNotFoundException extends RuntimeException {

        private Long id;
        private final String i18nKey = "shoppingList.idDoesNotExist";

        ShoppingListNotFoundException(Long id) {
            super(String.format("Shopping List ID does not exist: %d", id));
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public String getI18nKey() {
            return i18nKey;
        }

        public Object[] getArgs() {
            Object[] args = {id};
            return args;
        }

    }

    @SuppressWarnings("serial")
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public static class ShoppingListAlreadyExistsException extends RuntimeException {

        private Long id;
        private final String i18nKey = "shoppingList.idDoesAlreadyExist";

        ShoppingListAlreadyExistsException(Long id) {
            super(String.format("Shopping List ID already exists: %d", id));
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public String getI18nKey() {
            return i18nKey;
        }

        public Object[] getArgs() {
            Object[] args = {id};
            return args;
        }

    }

    @SuppressWarnings("serial")
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public static class ShoppingListIdNotNullException extends RuntimeException {

        private final String i18nKey = "shoppingList.idIsNotNull";

        public ShoppingListIdNotNullException() {
            super(String.format("Shopping list ID is not null"));
        }

        public String getI18nKey() {
            return i18nKey;
        }

        public Object[] getArgs() {
            Object[] args = {};
            return args;
        }

    }
}
