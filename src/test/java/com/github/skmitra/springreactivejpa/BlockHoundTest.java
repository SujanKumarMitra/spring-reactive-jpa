package com.github.skmitra.springreactivejpa;

import org.junit.jupiter.api.Test;
import reactor.blockhound.BlockingOperationError;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author skmitra
 * @since Aug 16/08/21, 2021
 */
class BlockHoundTest {

//    @BeforeAll
//    static void beforeAll() {
//        BlockHound.install();
//    }

    private Integer blockThenSupply() {
        System.out.println("Thread is non blocking: " + Schedulers.isInNonBlockingThread());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 10;
    }

    @Test
    void givenBlockingCall_blockHoundShouldBlock() {
        Mono<Integer> blockingMono = Mono.fromSupplier(this::blockThenSupply)
                .subscribeOn(Schedulers.parallel());

        StepVerifier.create(blockingMono)
                .expectErrorSatisfies(th -> {
                    System.err.println(th);
                    assertEquals(BlockingOperationError.class, th.getClass());
                })
                .verify();
    }
}
