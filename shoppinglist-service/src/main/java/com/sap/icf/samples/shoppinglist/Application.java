package com.sap.icf.samples.shoppinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.sap.icd.odatav2.spring.SpringODataLibraryPackageMarker;

@SpringBootApplication
@ComponentScan(basePackageClasses = { ShoppingListPackageMarker.class, SpringODataLibraryPackageMarker.class })
public class Application
{
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}