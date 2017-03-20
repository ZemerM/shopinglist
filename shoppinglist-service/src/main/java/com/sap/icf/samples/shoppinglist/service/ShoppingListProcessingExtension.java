package com.sap.icf.samples.shoppinglist.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.provider.ComplexType;
import org.apache.olingo.odata2.api.edm.provider.Property;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmExtension;
import org.apache.olingo.odata2.jpa.processor.api.model.JPAEdmSchemaView;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
@Component
public class ShoppingListProcessingExtension implements JPAEdmExtension, ApplicationContextAware
{
   
//	@Value("classpath:edm-mappings.xml")
//	private Resource mappingsResource;
	private ApplicationContext applicationContext;

    @Override
    public InputStream getJPAEdmMappingModelStream()
    {
    	ClassPathResource mappingsResource = (ClassPathResource) applicationContext.getResource("classpath:edm-mappings.xml");
        try
        {
            return ((ClassPathResource) mappingsResource).getInputStream();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }


	@Override
	public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		 this.applicationContext = applicationContext;
	}


	@Override
	public void extendJPAEdmSchema(JPAEdmSchemaView arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void extendWithOperation(JPAEdmSchemaView arg0) {
		// TODO Auto-generated method stub
		
	}





}