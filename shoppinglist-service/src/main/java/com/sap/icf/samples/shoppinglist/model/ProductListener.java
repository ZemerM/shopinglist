package com.sap.icf.samples.shoppinglist.model;

import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sap.icd.odatav2.spring.exceptions.UiRelevantRuntimeException;
import com.sap.icd.odatav2.spring.listeners.EntityListener;
import com.sap.icd.odatav2.spring.messages.Message;
import com.sap.xs2.security.container.UserInfo;
import com.sap.xs2.security.container.UserInfoException;

@EntityListener(entities = {Product.class})
public class ProductListener {

	@Autowired
	private UserInfo userInfo;
    
	private Logger logger = LoggerFactory.getLogger(ch.qos.logback.classic.Logger.class);
	
	/*
	 * Callback Types for Checks:
	 * 
	 * @PrePersist Executed before the entity manager persist operation is
	 * actually executed or cascaded. This call is synchronous with the persist
	 * operation.
	 * 
	 * @PreRemove Executed before the entity manager remove operation is
	 * actually executed or cascaded. This call is synchronous with the remove
	 * operation.
	 * 
	 * @PostPersist Executed after the entity manager persist operation is
	 * actually executed or cascaded. This call is invoked after the database
	 * INSERT is executed.
	 * 
	 * @PostRemove Executed after the entity manager remove operation is
	 * actually executed or cascaded. This call is synchronous with the remove
	 * operation.
	 * 
	 * @PreUpdate Executed before the database UPDATE operation.
	 * 
	 * @PostUpdate Executed after the database UPDATE operation.
	 * 
	 * @PostLoad Executed after an entity has been loaded into the current
	 * persistence context or an entity has been refreshed.
     */
    @PostLoad
    public void checkDisplayAuthorization(Object o) {

    	//To simplify testing, the read check is disabled by default.

//        try {
//
//            boolean hasDisplayScope = userInfo.checkLocalScope("DisplayProduct");
//            if (hasDisplayScope == false) {
//                throw new UiRelevantRuntimeException(Message.builder().code("product.displayAuthorizationMissing")
//                        .severity(Message.Severity.ERROR).build());
//            }
//        } catch (UserInfoException exception) {
//            throw new UiRelevantRuntimeException(Message.builder().code("product.displayAuthorizationMissing")
//                    .severity(Message.Severity.ERROR).build());
//        }
    }

    @PrePersist
    @PreRemove
    @PreUpdate
    public void checkEditAuthorization(Object o) {

        try {
            boolean hasChangeScope = userInfo.checkLocalScope("EditProduct");

            if (hasChangeScope == false) {
                throw new UiRelevantRuntimeException(Message.builder().code("product.editAuthorizationMissing")
                        .severity(Message.Severity.ERROR).build());
            }
        } catch (UserInfoException exception) {
            throw new UiRelevantRuntimeException(Message.builder().code("product.editAuthorizationMissing")
                    .severity(Message.Severity.ERROR).build());
        }
    }


}
