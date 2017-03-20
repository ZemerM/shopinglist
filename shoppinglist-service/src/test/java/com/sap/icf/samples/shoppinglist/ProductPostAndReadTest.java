/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sap.icf.samples.shoppinglist;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.sap.icf.samples.shoppinglist.config.LocalSecurityConfig;
import com.sap.icf.samples.shoppinglist.dto.Product;
import com.sap.icf.samples.shoppinglist.utils.JwtGenerator;

/**
 * @author d025461
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
@ActiveProfiles("default")
public class ProductPostAndReadTest {

    @Autowired
    TestRestTemplate restTemplate;
    
    private String jwt;

    @Before
    public void setUp() {
        jwt = new JwtGenerator().getTokenForAuthorizationHeader(LocalSecurityConfig.XSAPPNAME + ".EditProduct", LocalSecurityConfig.XSAPPNAME + ".DisplayProduct");    	
        Product product = new Product();	
        product.setProductCategoryId(1);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwt);
        HttpEntity<Product> entity = new HttpEntity<>(product, headers);
        restTemplate.exchange("/products", HttpMethod.POST, entity, Object.class);
        product = new Product();
        product.setProductCategoryId(2);
        entity = new HttpEntity<>(product, headers);
        restTemplate.exchange("/products", HttpMethod.POST, entity, Object.class);
    }

    @Test
    public void testGetApiAsJson() throws Exception {
        Boolean found1 = false;
        Boolean found2 = false;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Product[]> response = restTemplate.exchange("/products", HttpMethod.GET, entity, Product[].class);
        Product[] products = response.getBody();
        for (Product product : products) {
            if (product.getProductCategoryId().equals(1)) {
                found1 = true;
            }
            if (product.getProductCategoryId().equals(2)) {
                found2 = true;
            }
        }

        assertThat(found1, is(true));
        assertThat(found2, is(true));
    }
}
