package com.example.service;

import java.util.List;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoGeneratorServiceTests {
	FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

	@Test
	public void namesFlux() {
		Flux<String> namesFlux = fluxAndMonoGeneratorService.namesFlux();

		StepVerifier.create(namesFlux)
				// .expectNext("alex", "ben", "clohe")
				.expectNext("alex")
				.expectNextCount(2)
				.verifyComplete();
	}

	@Test
	public void nameMono() {
		Mono<String> nameMono = fluxAndMonoGeneratorService.nameMono();

		StepVerifier.create(nameMono)
				.expectNextCount(1)
				.verifyComplete();
	}

	@Test
	public void namesFlux_map() {
		Flux<String> nameFluxUpperCased = fluxAndMonoGeneratorService.namesFlux_map(3);

		StepVerifier.create(nameFluxUpperCased)
		.expectNext("4-ALEX")
		.expectNextCount(1)
		.verifyComplete();
	}

	@Test
	public void namesFlux_immutability() {
		Flux<String> nameFluxImmutability = fluxAndMonoGeneratorService.namesFlux_immutability();

		StepVerifier.create(nameFluxImmutability)
		.expectNext("alex")
		.expectNextCount(2)
		.verifyComplete();
	}


	@Test
	public void splitNameIntoCharFlux() {
		Flux<String> nameChars = fluxAndMonoGeneratorService.splitNameIntoCharFlux("ratul");

		StepVerifier.create(nameChars)
				.expectNext("r", "a", "t", "u")
				.expectNextCount(1)
				.verifyComplete();
	}

	@Test
	public void namesFlux_flatMap() {
		Flux<String> nameFluxChars = fluxAndMonoGeneratorService.namesFlux_flatMap(3);

		StepVerifier.create(nameFluxChars)
				.expectNext("A", "L", "E", "X")
				.expectNextCount(5)
				.verifyComplete();
	}

	@Test
	public void namesFlux_flatMap_async() {
		Flux<String> nameFluxChars = fluxAndMonoGeneratorService.namesFlux_flatMap_async(3);

		StepVerifier.create(nameFluxChars)
				.expectNextCount(9)
				.verifyComplete();
	}

	@Test
	public void namesFlux_concatMap_async() {
		Flux<String> nameFluxChars = fluxAndMonoGeneratorService.namesFlux_concatMap_async(3);

		StepVerifier.create(nameFluxChars)
				.expectNext("A", "L", "E", "X")
				.expectNextCount(5)
				.verifyComplete();
	}

	@Test
	public void namesFlux_transform() {
		Flux<String> nameFluxUpperCased = fluxAndMonoGeneratorService.namesFlux_transform(3);

		StepVerifier.create(nameFluxUpperCased)
		.expectNext("4-ALEX")
		.expectNextCount(1)
		.verifyComplete();
	}

	@Test
	public void namesFlux_transform_defaultIfEmpty() {
		String defaultValue = "default";

		Flux<String> nameFluxUpperCased = fluxAndMonoGeneratorService.namesFlux_transform_defaultIfEmpty(6, defaultValue);

		StepVerifier.create(nameFluxUpperCased)
		.expectNext(defaultValue)
		.verifyComplete();
	}

	@Test
	public void namesFlux_flatMap_transform_swiftIfEmpty() {
		String defaultValue = "default";
		Flux<String> nameFluxChars = fluxAndMonoGeneratorService.namesFlux_flatMap_transform_swiftIfEmpty(6,
				defaultValue);

		StepVerifier.create(nameFluxChars)
				.expectNext("D", "E", "F", "A", "U", "L", "T")
				.verifyComplete();
	}

	// Mono tests
	@Test
	public void nameMono_map_filter() {

		Mono<String> nameMono = fluxAndMonoGeneratorService.nameMono_map_filter(3);

		StepVerifier.create(nameMono)
				.expectNext("ALEX")
				.verifyComplete();
	}

	@Test
	public void nameMono_flatMap() {
		Mono<List<String>> nameMono = fluxAndMonoGeneratorService.nameMono_flatMap(3);

		StepVerifier.create(nameMono)
				.expectNext(List.of("a", "l", "e", "x"))
				.verifyComplete();
	}

	@Test
	public void nameMono_flatMapMany() {
		Flux<String> nameMono = fluxAndMonoGeneratorService.nameMono_flatMapMany(3);

		StepVerifier.create(nameMono)
				.expectNext("a", "l")
				.expectNext("e", "x")
				.verifyComplete();
	}

}
