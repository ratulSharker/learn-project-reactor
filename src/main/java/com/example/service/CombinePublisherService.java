package com.example.service;

import java.time.Duration;
import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CombinePublisherService {
	
	public Flux<String> contact() {
		Flux<String> abcFlux = Flux.fromIterable(List.of("a", "b", "c"))
				.delayElements(Duration.ofMillis(300));
		Flux<String> defFlux = Flux.fromIterable(List.of("d", "e", "f"));

		// `defFlux` subscribed after `abcFlux` is 
		return Flux.<String>concat(abcFlux, defFlux).log();
	}

	public Flux<String> contactWith() {
		Mono<String> aMono = Mono.just("a");
		Flux<String> defFlux = Flux.fromIterable(List.of("d", "e", "f"));

		return aMono.concatWith(defFlux).log();
	}

	public Flux<String> merge() {
		Flux<String> abcFlux = Flux.just("a", "b", "c")
		.delayElements(Duration.ofMillis(100));
		Flux<String> defFlux = Flux.just("d", "e", "f")
		.delayElements(Duration.ofMillis(110));

		return Flux.merge(abcFlux, defFlux).log();
	}

	public Flux<String> mergeWith() {
		Mono<String> aMono = Mono.just("a")
		.delayElement(Duration.ofMillis(100));

		Flux<String> defFlux = Flux.just("d", "e", "f");

		return aMono.mergeWith(defFlux).log();
	}

	public Flux<String> mergeSequential() {
		Flux<String> abcFlux = Flux.just("a", "b", "c")
		.delayElements(Duration.ofMillis(500));
		Flux<String> defFlux = Flux.just("d", "e", "f");

		return Flux.mergeSequential(abcFlux, defFlux).log();
	}

	public Flux<String> zip_flux() {
		Flux<String> abcFlux = Flux.just("a", "b", "c")
		.delayElements(Duration.ofMillis(500));
		Flux<String> defFlux = Flux.just("d", "e", "f");

		return Flux.zip(abcFlux, defFlux, (first, second) -> first + second).log();
	}

	public Flux<String> zip_flux_tuple() {
		Flux<String> abcFlux = Flux.just("a", "b", "c")
		.delayElements(Duration.ofMillis(500));
		Flux<String> defFlux = Flux.just("d", "e", "f");
		Flux<String> _123Flux = Flux.just("1", "2", "3");
		Flux<String> _456Flux = Flux.just("4", "5", "6");

		return Flux.zip(abcFlux, defFlux, _123Flux, _456Flux).map(t4 -> {
			return t4.getT1() + t4.getT2() + t4.getT3() + t4.getT4();
		});
	}

	public Flux<String> zipWith_flux() {
		Flux<String> abcFlux = Flux.just("a", "b", "c")
				.delayElements(Duration.ofMillis(500));
		Flux<String> defFlux = Flux.just("d", "e", "f");

		return abcFlux.zipWith(defFlux, (first, second) -> first + second);
	}

	public Mono<String> zipWith_mono() {
		Mono<String> aMono = Mono.just("a");
		Mono<String> bMono = Mono.just("b");

		return aMono.zipWith(bMono, (first, second) -> first + second);
	}
}
