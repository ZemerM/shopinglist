package com.sap.icf.samples.shoppinglist.config;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

// Config class for running locally against M2.
@Configuration
@Profile({"default"})
public class LocalDbConfig {

    @Bean
    public JpaVendorAdapter eclipseLink() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean emf(JpaVendorAdapter adapter, DataSource ds,
            @Value("${com.sap.icd.odatav2.spring.persistenceUnit}") String persistenceUnitName) {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(adapter);
        factory.setDataSource(ds);
        factory.setPersistenceUnitName(persistenceUnitName);
        factory.setJpaPropertyMap(getJpaProperties());
        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    private Map<String, String> getJpaProperties() {
        Map<String, String> map = new HashMap<>();
        map.put("eclipselink.ddl-generation", "create-or-extend-tables");
        map.put("eclipselink.ddl-generation.output-mode", "both");
        return map;
    }

}
