package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.model.Movie;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MovieReactiveServiceTests {
	
	private MovieReactiveService movieReactiveService;

	public MovieReactiveServiceTests() {
		MovieInfoService movieInfoService = new MovieInfoService();
		ReviewService reviewService = new ReviewService();
		this.movieReactiveService = new MovieReactiveService(movieInfoService, reviewService);
	}

	@Test
	public void getAllMovies() {
		Flux<Movie> moviesFlux = movieReactiveService.getAllMovies();

		StepVerifier.create(moviesFlux)
		.assertNext(movie -> {
			assertEquals(movie.getMovieInfo().getMovieInfoId(), 100L);
			assertEquals(movie.getReviewList().size(), 2);
		})
		.assertNext(movie -> {
			assertEquals(movie.getMovieInfo().getMovieInfoId(), 101L);
			assertEquals(movie.getReviewList().size(), 2);
		}).assertNext(movie -> {
			assertEquals(movie.getMovieInfo().getMovieInfoId(), 102L);
			assertEquals(movie.getReviewList().size(), 2);
		})
		.verifyComplete();
	}

	@Test
	public void getMovieById() {
		Long movieInfoId = 100L;
		Mono<Movie> movieMono = movieReactiveService.getMovieById(movieInfoId);

		StepVerifier.create(movieMono)
				.assertNext(movie -> {
					assertEquals(movie.getMovieInfo().getMovieInfoId(), movieInfoId);
					assertEquals(movie.getReviewList().size(), 2);
				})
				.verifyComplete();
	}

}
