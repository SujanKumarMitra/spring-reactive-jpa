package com.github.skmitra.springreactivejpa;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author skmitra
 * @since Aug 16/08/21, 2021
 */
@RequiredArgsConstructor
public class BlockingEncapsulationReactiveCustomerDao implements ReactiveCustomerDao {

    private final CustomerRepository repository;

    @Override
    public Flux<Customer> findAll() {
        return Mono.fromCallable(repository::findAll)
                .publishOn(Schedulers.boundedElastic())
                .flatMapMany(Flux::fromIterable);
    }

    @Override
    public Mono<Customer> findById(long customerId) {
        return Mono.fromCallable(() -> repository.findById(customerId))
                .publishOn(Schedulers.boundedElastic())
                .mapNotNull(op -> op.orElseGet(() -> null));
    }

    @Override
    public Mono<Customer> save(Customer customer) {
        return Mono.just(customer)
                .publishOn(Schedulers.boundedElastic())
                .map(repository::save);
    }

    @Override
    public Mono<Void> deleteById(long customerId) {
        return Mono.just(customerId)
                .publishOn(Schedulers.boundedElastic())
                .handle((cId, sink) -> {
                    try {
                        repository.deleteById(cId);
                        sink.complete();
                    } catch (Throwable th) {
                        sink.error(th);
                    }
                });
    }
}
