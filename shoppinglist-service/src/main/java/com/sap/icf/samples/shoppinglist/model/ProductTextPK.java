package com.sap.icf.samples.shoppinglist.model;

import java.io.Serializable;

/**
 * ID class for entity: CountryText1
 *
 */
public class ProductTextPK implements Serializable {

    private String language;
    private Long productId;
    private static final long serialVersionUID = 1L;

    public ProductTextPK() {
    }

    public ProductTextPK(String language, Long productId) {
        this.language = language;
        this.productId = productId;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /*
	 * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ProductTextPK)) {
            return false;
        }
        ProductTextPK other = (ProductTextPK) o;
        return true
                && (getLanguage() == null ? other.getLanguage() == null : getLanguage().equals(other.getLanguage()))
                && (getProductId() == null ? other.getProductId() == null : getProductId().equals(other.getProductId()));
    }

    /*	 
	 * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (getLanguage() == null ? 0 : getLanguage().hashCode());
        result = prime * result + (getProductId() == null ? 0 : getProductId().hashCode());
        return result;
    }

}
