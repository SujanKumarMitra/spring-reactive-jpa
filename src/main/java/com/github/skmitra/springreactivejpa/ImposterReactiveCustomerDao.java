package com.github.skmitra.springreactivejpa;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author skmitra
 * @since Aug 15/08/21, 2021
 */
@RequiredArgsConstructor
public class ImposterReactiveCustomerDao implements ReactiveCustomerDao {

    private final CustomerRepository repository;

    @Override
    public Flux<Customer> findAll() {
        return Flux.fromIterable(repository.findAll());
    }

    @Override
    public Mono<Customer> findById(long customerId) {
        return Mono.justOrEmpty(repository.findById(customerId));
    }

    @Override
    public Mono<Customer> save(Customer customer) {
        return Mono.just(repository.save(customer));
    }

    @Override
    public Mono<Void> deleteById(long customerId) {
        repository.deleteById(customerId);
        return Mono.empty();
    }
}
