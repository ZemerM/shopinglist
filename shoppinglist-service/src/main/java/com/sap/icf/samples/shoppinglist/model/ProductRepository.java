package com.sap.icf.samples.shoppinglist.model;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
