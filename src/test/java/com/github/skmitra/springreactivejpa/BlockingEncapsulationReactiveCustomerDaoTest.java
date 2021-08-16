package com.github.skmitra.springreactivejpa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

/**
 * @author skmitra
 * @since Aug 16/08/21, 2021
 */
class BlockingEncapsulationReactiveCustomerDaoTest {
    public static final String VALID_NAME = "VALID_NAME";
    private CustomerRepository repository;
    private BlockingEncapsulationReactiveCustomerDao customerDao;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(CustomerRepository.class);
        customerDao = new BlockingEncapsulationReactiveCustomerDao(repository);
    }

    @Test
    void givenBlockingRepository_whenFetchedCustomerWithId_blockHoundShouldNotIntercept() {
        Mockito.doAnswer(invocation -> {
            Thread.sleep(100);
            return Optional.of(new Customer(1L, VALID_NAME));
        }).when(repository).findById(1L);


        Mono<Customer> customerMono = Mono.just(1L)
                .log()
                .publishOn(Schedulers.parallel())
                .flatMap(customerDao::findById)
                .subscribeOn(Schedulers.immediate());

        StepVerifier.create(customerMono)
                .expectNext(new Customer(1L,VALID_NAME))
                .expectComplete()
                .verify();
    }
}