package com.example.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMonoGeneratorService {

	// Flux
	public Flux<String> namesFlux() {
		return Flux.fromIterable(List.of("alex", "ben", "clohe")).log();
	}

	public Flux<String> namesFlux_map(int minimumNameLength) {
		return namesFlux()
			.map(name -> name.toUpperCase())
			.filter(name -> name.length() > minimumNameLength) // 4-ALEX, 5-CLOHE
			.map(name -> name.length() + "-" + name)
			.doOnNext(name -> {
				System.out.println("Name is : " + name);
			})
			.doOnSubscribe(subscription -> {
				System.out.println("Subscription is : " + subscription);
			})
			.doOnComplete(() -> {
				System.out.println("Flux is completed");
			})
			.doFinally(signalType -> {
				System.out.println("Inside doFinally : " + signalType );
			})
			.log();
	}

	public Flux<String> namesFlux_flatMap(int minimumNameLength) {
		return namesFlux()
		.map(String::toUpperCase)
		.filter(name -> name.length() > minimumNameLength)
		.flatMap(name -> splitNameIntoCharFlux(name))
		.log();		
	}

	public Flux<String> splitNameIntoCharFlux(String name) {
		String[] nameChars = name.split("");
		return Flux.fromArray(nameChars);
	}

	public Flux<String> namesFlux_flatMap_async(int minimumNameLength) {
		return namesFlux()
		.map(String::toUpperCase)
		.filter(name -> name.length() > minimumNameLength)
		.flatMap(name -> splitNameIntoCharFluxDelay(name))
		.log();		
	}

	public Flux<String> namesFlux_concatMap_async(int minimumNameLength) {
		return namesFlux()
		.map(String::toUpperCase)
		.filter(name -> name.length() > minimumNameLength)
		.concatMap(name -> splitNameIntoCharFluxDelay(name))
		.log();		
	}

	public Flux<String> splitNameIntoCharFluxDelay(String name) {
		String[] nameChars = name.split("");
		int random = new Random(13).nextInt(1000);
		return Flux.fromArray(nameChars).delayElements(Duration.ofMillis(random));
	}

	public Flux<String> namesFlux_immutability() {
		Flux<String> namesFlux = namesFlux();
		namesFlux.map(String::toUpperCase);
		return namesFlux;
	}

	public Flux<String> namesFlux_transform(int minimumNameLength) {

		Function<Flux<String>, Flux<String>> transform = flux -> flux.map(String::toUpperCase)
				.filter(name -> name.length() > minimumNameLength) // 4-ALEX, 5-CLOHE
				.map(name -> name.length() + "-" + name);

		return namesFlux()
				.transform(transform)
				.log();
	}

	public Flux<String> namesFlux_transform_defaultIfEmpty(int minimumNameLength, String defaultValue) {
		Function<Flux<String>, Flux<String>> transform = flux -> flux.map(String::toUpperCase)
				.filter(name -> name.length() > minimumNameLength) // 4-ALEX, 5-CLOHE
				.map(name -> name.length() + "-" + name);

		return namesFlux()
				.transform(transform)
				.defaultIfEmpty(defaultValue)
				.log();
	}

	public Flux<String> namesFlux_flatMap_transform_swiftIfEmpty(int minimumNameLength, String defaultValue) {

		Function<Flux<String>, Flux<String>> transform = flux -> flux
				.map(String::toUpperCase)
				.flatMap(name -> splitNameIntoCharFlux(name));

		Flux<String> alternativeFlux = Flux.just(defaultValue)
				.transform(transform);

		return namesFlux()
				.filter(name -> name.length() > minimumNameLength)
				.transform(transform)
				.switchIfEmpty(alternativeFlux)
				.log();
	}
	

	// Mono
	public Mono<String> nameMono() {
		return Mono.just("alex").log();
	}

	public Mono<String> nameMono_map_filter(int minimumNameLength) {
		return nameMono()
		.map(String::toUpperCase)
		.filter(name -> name.length() > minimumNameLength);
	}

	public Mono<List<String>> nameMono_flatMap(int minimumNameLength) {
		return nameMono()
		.filter(name -> name.length() > minimumNameLength)
		.flatMap(this::splitNameMono);
	}

	public Flux<String> nameMono_flatMapMany(int minimumNameLength) {
		return nameMono()
		.filter(name -> name.length() > minimumNameLength)
		.flatMapMany(this::splitNameIntoCharFlux)
		.log();
	}

	public Mono<List<String>> splitNameMono(String name) {
		String[] chars = name.split("");
		return Mono.just(List.of(chars));
	}
}
