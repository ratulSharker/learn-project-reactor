package com.example.service;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class CombinePublisherServiceTests {
	
	public CombinePublisherService combinePublisherService = new CombinePublisherService();

	@Test
	public void concat() {
		Flux<String> abcdefFlux = combinePublisherService.contact();

		StepVerifier.create(abcdefFlux)
				.expectNext("a", "b", "c", "d", "e", "f")
				.verifyComplete();
	}

	@Test
	public void concatWith() {
		Flux<String> adefFlux = combinePublisherService.contactWith();

		StepVerifier.create(adefFlux)
				.expectNext("a", "d", "e", "f")
				.verifyComplete();
	}

	@Test
	public void merge() {
		Flux<String> mergedFlux = combinePublisherService.merge();

		StepVerifier.create(mergedFlux)
				.expectNext("a", "d", "b", "e", "c", "f")
				// .expectNextCount(6)
				.verifyComplete();
	}

	@Test
	public void mergeWith() {
		Flux<String> mergedFlux = combinePublisherService.mergeWith();

		StepVerifier.create(mergedFlux)
				.expectNext("d", "e", "f", "a")
				.verifyComplete();
	}

	@Test
	public void mergeSequential() {
		Flux<String> mergedFlux = combinePublisherService.mergeSequential();

		StepVerifier.create(mergedFlux)
				.expectNext("a", "b", "c", "d", "e", "f")
				.verifyComplete();
	}

	@Test
	public void zip_flux() {
		Flux<String> zippedFlux = combinePublisherService.zip_flux();

		StepVerifier.create(zippedFlux)
				.expectNext("ad", "be", "cf")
				.verifyComplete();
	}

	@Test
	public void zip_flux_tuple() {
		Flux<String> zippedFlux = combinePublisherService.zip_flux_tuple();

		StepVerifier.create(zippedFlux)
				.expectNext("ad14", "be25", "cf36")
				.verifyComplete();
	}

	@Test
	public void zipWith_flux() {
		Flux<String> zippedFlux = combinePublisherService.zipWith_flux();

		StepVerifier.create(zippedFlux)
				.expectNext("ad", "be", "cf")
				.verifyComplete();
	}

	@Test
	public void zipWith_mono() {
		Mono<String> zippedMono = combinePublisherService.zipWith_mono();

		StepVerifier.create(zippedMono)
				.expectNext("ab")
				.verifyComplete();
	}
}
