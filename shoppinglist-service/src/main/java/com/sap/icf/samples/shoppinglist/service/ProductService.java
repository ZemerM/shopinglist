/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sap.icf.samples.shoppinglist.service;

import com.sap.icf.samples.shoppinglist.model.Product;
import com.sap.icf.samples.shoppinglist.model.ProductRepository;
import com.sap.icf.samples.shoppinglist.model.ProductText;
import com.sap.icf.samples.shoppinglist.model.ProductTextPK;
import com.sap.icf.samples.shoppinglist.model.ProductTextRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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
public class ProductService {

    @PersistenceContext
    private EntityManager em;
    private Logger logger = LoggerFactory.getLogger(ch.qos.logback.classic.Logger.class);
    private ProductRepository repo;
    private ProductTextRepository textRepo;
    private Mapper mapper;

    @Autowired
    public ProductService(EntityManager em, ProductRepository repo, ProductTextRepository textRepo, Mapper mapper) {
        this.em = em;
        this.repo = repo;
        this.textRepo = textRepo;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<com.sap.icf.samples.shoppinglist.dto.Product> getProducts(Integer pageId, Integer pageSize) {
        em.setProperty("LANGUAGE", "*");
        List<com.sap.icf.samples.shoppinglist.dto.Product> productDtoList = new ArrayList<>();
        for (Product product : repo.findAll(new PageRequest(pageId, pageSize))) {
            productDtoList.add(mapper.toDto(product));
        }
        return productDtoList;

    }

    @Transactional
    public Long postProduct(com.sap.icf.samples.shoppinglist.dto.Product productDto) {
        em.setProperty("LANGUAGE", "*");
        throwIfNotNull(productDto.getProductId());
        Product product = mapper.toEntity(productDto);
        List<ProductText> texts = product.getProductTexts();
        product.setProductTexts(new ArrayList<ProductText>());
        repo.save(product);
        texts.stream().forEach(text -> text.setProductId(product.getProductId()));
        product.setProductTexts(texts);
        repo.save(product);
        return product.getProductId();
    }

    @Transactional(readOnly = true)
    public com.sap.icf.samples.shoppinglist.dto.Product getProduct(Long id) {
        em.setProperty("LANGUAGE", "*");
        throwIfNotExists(id);
        Product product = repo.findOne(id);
        return mapper.toDto(product);
    }

    @Transactional
    public void putProduct(Long id, com.sap.icf.samples.shoppinglist.dto.Product productDto) {
        em.setProperty("LANGUAGE", "*");
        throwIfNotExists(id);
        productDto.setProductId(id);
        Product product = mapper.toEntity(productDto);
        repo.save(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        em.setProperty("LANGUAGE", "*");
        repo.delete(id);
    }

    @Transactional(readOnly = true)
    public List<com.sap.icf.samples.shoppinglist.dto.ProductText> getProductTexts(Long id) {
        em.setProperty("LANGUAGE", "*");
        List<com.sap.icf.samples.shoppinglist.dto.ProductText> productTextDtoList = new ArrayList<>();
        throwIfNotExists(id);
        textRepo.findByProductId(id).forEach((_item) -> {
            productTextDtoList.add(mapper.toDto(_item));
        });
        return productTextDtoList;

    }

    @Transactional(readOnly = true)
    public com.sap.icf.samples.shoppinglist.dto.ProductText getProductText(Long id, String language) {
        em.setProperty("LANGUAGE", language);
        throwIfNotExists(id);
        throwIfNotExists(id, language);
        return mapper.toDto(textRepo.findOne(new ProductTextPK(language, id)));
    }

    @Transactional
    public void putProductText(Long id, String language, com.sap.icf.samples.shoppinglist.dto.ProductText productTextDto) {
        em.setProperty("LANGUAGE", language);
        throwIfNotExists(id);
        ProductText productText = mapper.toEntity(productTextDto);
        productText.setProductId(id);
        productText.setLanguage(language);
        textRepo.save(productText);
    }

    @Transactional
    public void deleteProductText(Long id, String language) {
        em.setProperty("LANGUAGE", language);
        throwIfNotExists(id);
        throwIfNotExists(id, language);
        textRepo.delete(new ProductTextPK(language, id));
    }

    // ------------------------------------------------------------------------------------------------
    // Helper
    // ------------------------------------------------------------------------------------------------
    private void throwIfNotExists(Long id) {
        if (id != null) {
            if (!repo.exists(id)) {
                ProductNotFoundException productNotFoundException = new ProductNotFoundException(id);
                logger.warn(productNotFoundException.getMessage());
                throw productNotFoundException;
            }
        }
    }

    private void throwIfNotExists(Long id, String Language) {
        ProductTextPK key = new ProductTextPK(Language, id);
        if (id != null) {
            if (!textRepo.exists(key)) {
                ProductTextNotFoundException productTextNotFoundException = new ProductTextNotFoundException(id, Language);
                logger.warn(productTextNotFoundException.getMessage());
                throw productTextNotFoundException;
            }
        }
    }

    private void throwIfExists(Long id) {
        if (id != null && repo.exists(id)) {
            ProductAlreadyExistsException productAlreadyExistsException = new ProductAlreadyExistsException(id);
            logger.warn(productAlreadyExistsException.getMessage());
            throw productAlreadyExistsException;
        }
    }

    private void throwIfNotNull(Long id) {
        if (id != null && id != 0) {
            ProductIdNotNullException exception = new ProductIdNotNullException();
            logger.warn(exception.getMessage());
            throw exception;
        }
    }

    @SuppressWarnings("serial")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class ProductNotFoundException extends RuntimeException {

        private Long id;
        private final String i18nKey = "product.idDoesNotExist";

        ProductNotFoundException(Long id) {
            super(String.format("Product ID does not exist: %d", id));
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public String getI18nKey() {
            return i18nKey;
        }

        public Object[] getArgs() {
            Object[] args = {id.toString()};
            return args;
        }
    }

    @SuppressWarnings("serial")
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public static class ProductTextNotFoundException extends RuntimeException {

        private Long id;
        private String language;
        private final String i18nKey = "productText.DoesNotExist";

        ProductTextNotFoundException(Long id, String language) {
            super(String.format("Product text with id %d and language %s does not exist", id, language));
            this.id = id;
            this.language = language;
        }

        public Long getId() {
            return id;
        }

        public String getI18nKey() {
            return i18nKey;
        }

        public String getLanguage() {
            return language;
        }

        public Object[] getArgs() {
            Object[] args = {id.toString(), language};
            return args;
        }

    }

    @SuppressWarnings("serial")
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public static class ProductAlreadyExistsException extends RuntimeException {

        private Long id;
        private final String i18nKey = "product.idDoesAlreadyExist";

        ProductAlreadyExistsException(Long id) {
            super(String.format("Product ID already exists: %d", id));
            this.id = id;
        }

        public Long getId() {
            return id;
        }

        public String getI18nKey() {
            return i18nKey;
        }

        public Object[] getArgs() {
            Object[] args = {id.toString()};
            return args;
        }

    }

    @SuppressWarnings("serial")
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public static class ProductIdNotNullException extends RuntimeException {

        private final String i18nKey = "product.idIsNotNull";

        public ProductIdNotNullException() {
            super(String.format("Product ID is not null"));
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
