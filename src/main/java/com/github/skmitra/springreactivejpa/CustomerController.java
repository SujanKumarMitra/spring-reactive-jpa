package com.github.skmitra.springreactivejpa;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author skmitra
 * @since Aug 15/08/21, 2021
 */
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final ReactiveCustomerDao customerDao;

    @GetMapping
    public Flux<Customer> getAll() {
        return customerDao.findAll();
    }

    @GetMapping("/{customerId}")
    public Mono<Customer> getOne(@PathVariable long customerId) {
        return customerDao.findById(customerId);
    }

    @PostMapping
    public Mono<Customer> save(@RequestBody Customer customer) {
        return customerDao.save(customer);
    }

    @DeleteMapping("/{customerId}")
    public Mono<Void> deleteById(@PathVariable long customerId) {
        return customerDao.deleteById(customerId);
    }
}
