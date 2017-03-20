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
import com.sap.icf.samples.shoppinglist.api.ProductsApi;
import com.sap.icf.samples.shoppinglist.dto.ProductText;
import com.sap.icf.samples.shoppinglist.service.ProductService;

@RestController
public class ProductRestControllerImpl implements ProductsApi {

    private static final String PAGE_SIZE_DEFAULT = "20";

    // TODO: Autowire Logger
    private Logger logger = LoggerFactory.getLogger(ch.qos.logback.classic.Logger.class);
    private ProductService productManager;

    /**
     * Read all Products from the database and return them in a list.
     */
    @Autowired
    public ProductRestControllerImpl(ProductService productManager) {
        this.productManager = productManager;
    }

    @Override
    public ResponseEntity<List<com.sap.icf.samples.shoppinglist.dto.Product>> productsGet(@RequestParam(value = "pageId", required = false, defaultValue = "0") Integer pageId, @RequestParam(value = "pageSize", required = false, defaultValue = PAGE_SIZE_DEFAULT) Integer pageSize) {
        return new ResponseEntity<>(productManager.getProducts(pageId, pageSize), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> productsPost(@RequestBody com.sap.icf.samples.shoppinglist.dto.Product productDto) {
        Long id = productManager.postProduct(productDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<com.sap.icf.samples.shoppinglist.dto.Product> productsIdGet(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productManager.getProduct(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> productsIdPut(@PathVariable("id") Long id,
            @RequestBody com.sap.icf.samples.shoppinglist.dto.Product productDto) {
        productManager.putProduct(id, productDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri());
        return new ResponseEntity<>(httpHeaders, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Void> productsIdDelete(@PathVariable("id") Long id) {
        productManager.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<ProductText>> productsIdTextsGet(@PathVariable("id") Long id) {
        return new ResponseEntity<>(productManager.getProductTexts(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> productsIdTextsLanguageDelete(@PathVariable("id") Long id, @PathVariable("language") String language) {
        productManager.deleteProductText(id, language);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ProductText> productsIdTextsLanguageGet(@PathVariable("id") Long id, @PathVariable("language") String language) {
        return new ResponseEntity<>(productManager.getProductText(id, language), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> productsIdTextsLanguagePut(@PathVariable("id") Long id, @PathVariable("language") String language, @RequestBody ProductText product) {
        productManager.putProductText(id, language, product);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
