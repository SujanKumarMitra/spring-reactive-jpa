package com.github.skmitra.springreactivejpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class SpringReactiveJpaApplication {

	public static void main(String[] args) {
		BlockHound.install();
		SpringApplication.run(SpringReactiveJpaApplication.class, args);
	}

}
