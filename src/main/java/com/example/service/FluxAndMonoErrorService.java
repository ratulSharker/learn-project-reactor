package com.example.service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class FluxAndMonoErrorService {
	
	// Flux
	public Flux<String> flux_error() {
		return Flux.just("a", "b", "c")
				.concatWith(Flux.error(new RuntimeException("Error after abc")))
				.concatWith(Flux.just("d")).log();
	}

	public Flux<Integer> flux_onErrorReturn() {
		Flux<Integer> numbersFlux = Flux.just(1, 2, 4, 0);

		return numbersFlux.map(number -> 100 / number)
		.onErrorReturn(-1)
		.log();
	}

	public Flux<Integer> flux_onErrorResume() {
		Flux<Integer> fallbackFlux = Flux.just(-1, -2);
		Flux<Integer> numbersFlux = Flux.just(1, 2, 4, 0);

		return numbersFlux.map(number -> 100 / number)
		.onErrorResume(ArithmeticException.class, ex -> {
			log.error(ex.getMessage(), ex);
			return fallbackFlux;
		})
		.log();
	}

	public Flux<Integer> flux_onErrorContinue() {
		Flux<Integer> numbersFlux = Flux.just(1, 2, 0, 4);

		return numbersFlux.map(number -> 100 / number)
		.onErrorContinue(ArithmeticException.class, (ex, number) -> {
			log.warn("Exception Message: \"{}\", For object \"{}\"", ex.getMessage(), number);
		})
		.log();
	}

	public Flux<Integer> flux_onErrorMap() {
		Flux<Integer> numbersFlux = Flux.just(1, 2, 0, 4);

		return numbersFlux.map(number -> 100 / number)
		.onErrorMap(ArithmeticException.class, ex -> {
			log.warn("Exception is : ", ex.getMessage());
			return new RuntimeException("User not active");
		})
		.log();
	}

	public Flux<Integer> flux_doOnError() {
		Flux<Integer> numbersFlux = Flux.just(1, 2, 0, 4);

		return numbersFlux.map(number -> 100 / number)
				.doOnError(ex -> {
					log.warn("Inside first doOnError: [{}]", ex.getMessage());					
				})
				.onErrorMap(ArithmeticException.class, ex -> {
					log.warn("Exception is : ", ex.getMessage());
					return new RuntimeException("User not active");
				})
				.doOnError(ex -> {
					log.warn("Inside second doOnError: [{}]", ex.getMessage());
				});
	}

	// Mono
	public Mono<String> mono_error() {
		return Mono.just("a")
		.map(value -> {
			throw new RuntimeException("Error occured");
		});
	}

	public Mono<Integer> mono_onErrorReturn() {
		return Mono.just(0)
		.map(value -> 100 / value)
		.onErrorReturn(-1);
	}

	public Mono<Integer> mono_onErrorResume() {
		return Mono.just(0)
		.map(value -> 100 / value)
		.onErrorResume(ArithmeticException.class, ex -> {
			log.warn("Exception message : [{}]", ex.getMessage());
			return Mono.just(10);
		});
	}

	public Mono<Integer> mono_onErrorContinue() {
		return Mono.just(0)
				.map(value -> 100 / value)
				.onErrorContinue((ex, value) -> {
					log.warn("Exception message : [{}], for value: [{}]", ex.getMessage(), value);
				});
	}

	public Mono<Integer> mono_onErrorMap() {
		return Mono.just(0)
		.map(value -> 100 / value)
		.onErrorMap((ex) -> {
			throw new RuntimeException("Cannot divide by zero");
		});
	}

	public Mono<Integer> mono_doOnError() {
		return Mono.just(0)
		.map(value -> 100 / value)
		.doOnError(ex -> {
			log.warn("First doOnError: [{}]", ex.getMessage());
		})
		.onErrorMap((ex) -> {
			throw new RuntimeException("Cannot divide by zero");
		})
		.doOnError(ex -> {
			log.warn("Second doOnError: [{}]", ex.getMessage());
		});
	}



}
