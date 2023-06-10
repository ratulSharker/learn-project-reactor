package com.example.service;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoErrorServiceTests {
	
	private FluxAndMonoErrorService fluxMonoErrorService = new FluxAndMonoErrorService();

	// Flux
	@Test
	public void flux_error() {
		Flux<String> stringFlux = fluxMonoErrorService.flux_error();

		StepVerifier.create(stringFlux)
		.expectNext("a", "b", "c")
		.expectError(RuntimeException.class)
		.verify();
	}

	@Test
	public void flux_error_1() {
		Flux<String> stringFlux = fluxMonoErrorService.flux_error();

		StepVerifier.create(stringFlux)
		.expectNext("a", "b", "c")
		.expectError()
		.verify();
	}

	@Test
	public void flux_error_2() {
		Flux<String> stringFlux = fluxMonoErrorService.flux_error();

		StepVerifier.create(stringFlux)
		.expectNext("a", "b", "c")
		.expectErrorMessage("Error after abc")
		.verify();
	}

	@Test
	public void flux_onErrorReturn() {
		Flux<Integer> numbers = fluxMonoErrorService.flux_onErrorReturn();

		StepVerifier.create(numbers)
				.expectNext(100, 50, 25, -1)
				.verifyComplete();
	}

	@Test
	public void flux_onErrorResume() {
		Flux<Integer> numbers = fluxMonoErrorService.flux_onErrorResume();

		StepVerifier.create(numbers)
				.expectNext(100, 50, 25, -1, -2)
				.verifyComplete();
	}

	@Test
	public void flux_onErrorContinue() {
		Flux<Integer> numbers = fluxMonoErrorService.flux_onErrorContinue();

		StepVerifier.create(numbers)
				.expectNext(100, 50, 25)
				.verifyComplete();
	}

	@Test
	public void flux_onErrorErrorMap() {
		Flux<Integer> numbers = fluxMonoErrorService.flux_onErrorMap();

		StepVerifier.create(numbers)
				.expectNext(100, 50)
				.expectErrorMessage("User not active")
				.verify();
	}

	@Test
	public void flux_doOnError() {
		Flux<Integer> numbers = fluxMonoErrorService.flux_doOnError();

		StepVerifier.create(numbers)
				.expectNext(100, 50)
				.expectErrorMessage("User not active")
				.verify();
	}

	// Mono
	@Test
	public void mono_error() {
		Mono<String> stringMono = fluxMonoErrorService.mono_error();

		StepVerifier.create(stringMono)
				.expectError(RuntimeException.class)
				.verify();
	}

	@Test
	public void mono_onErrorReturn() {
		Mono<Integer> integerMono = fluxMonoErrorService.mono_onErrorReturn();

		StepVerifier.create(integerMono)
				.expectNext(-1)
				.verifyComplete();
	}

	@Test
	public void mono_onErrorResume() {
		Mono<Integer> integerMono = fluxMonoErrorService.mono_onErrorResume();

		StepVerifier.create(integerMono)
				.expectNext(10)
				.verifyComplete();
	}

	@Test
	public void mono_onErrorContinue() {
		Mono<Integer> integerMono = fluxMonoErrorService.mono_onErrorContinue();

		StepVerifier.create(integerMono)
				.verifyComplete();
	}

	@Test
	public void mono_onErrorMap() {
		Mono<Integer> integerMono = fluxMonoErrorService.mono_onErrorMap();

		StepVerifier.create(integerMono)
				.expectErrorMessage("Cannot divide by zero")
				.verify();
	}

	@Test
	public void mono_doOnError() {
		Mono<Integer> integerMono = fluxMonoErrorService.mono_doOnError();

		StepVerifier.create(integerMono)
				.expectErrorMessage("Cannot divide by zero")
				.verify();
	}

}
