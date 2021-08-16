package com.github.skmitra.springreactivejpa;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author skmitra
 * @since Aug 16/08/21, 2021
 */
public interface ReactiveCustomerDao {
    Flux<Customer> findAll();

    Mono<Customer> findById(long customerId);

    Mono<Customer> save(Customer customer);

    Mono<Void> deleteById(long customerId);
}
