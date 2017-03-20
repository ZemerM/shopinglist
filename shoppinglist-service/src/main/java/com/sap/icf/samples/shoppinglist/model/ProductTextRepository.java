package com.sap.icf.samples.shoppinglist.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ProductTextRepository extends JpaRepository<ProductText, ProductTextPK> {

    @Transactional
    void deleteByProductId(Long id);
    List<ProductText> findByProductId(Long id);
}
