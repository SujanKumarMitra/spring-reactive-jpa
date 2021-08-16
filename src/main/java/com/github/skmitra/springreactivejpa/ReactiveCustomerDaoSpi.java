package com.github.skmitra.springreactivejpa;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author skmitra
 * @since Aug 16/08/21, 2021
 */
@Configuration
public class ReactiveCustomerDaoSpi {

    @Bean
    public ReactiveCustomerDao reactiveCustomerDao(@Value("${dao.type:NON_BLOCKING}") String beanId, CustomerRepository repository) {
        switch (beanId) {
            case "BLOCKING":
                return new ImposterReactiveCustomerDao(repository);
            case "NON_BLOCKING":
                return new BlockingEncapsulationReactiveCustomerDao(repository);
            default:
                throw new NoSuchBeanDefinitionException("Invalid Dao Type" + beanId);
        }
    }
}
