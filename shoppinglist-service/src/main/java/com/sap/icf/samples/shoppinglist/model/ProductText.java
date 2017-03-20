package com.sap.icf.samples.shoppinglist.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.AdditionalCriteria;

@Entity
@Table(name="\"com.sap.icf.samples::ShoppingList.ProductText\"")
@IdClass(ProductTextPK.class)
@Cacheable(false)
@AdditionalCriteria(":LANGUAGE = \"*\" or this.language=:LANGUAGE")

public class ProductText implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "\"ProductId\"")
	private Long productId;
	
	
	@Id
	@Column(name = "\"Language\"")
	private String language;
	
	@Column(name = "\"ProductName\"")
	private String productName;
	
	@Column(name = "\"ProductDescription\"")
	private String productDescription;

	public ProductText() {
		super();
	}
	
	public Long getProductId() {
		return this.productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String langauge) {
		this.language = langauge;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	
}
