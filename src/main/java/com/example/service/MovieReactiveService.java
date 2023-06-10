package com.example.service;

import java.util.List;

import com.example.model.Movie;
import com.example.model.MovieInfo;
import com.example.model.Review;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class MovieReactiveService {

	private MovieInfoService movieInfoService;
	private ReviewService reviewService;
	
	public Flux<Movie> getAllMovies() {
		Flux<MovieInfo> moviesFlux = movieInfoService.retrieveMoviesFlux();
		return moviesFlux.flatMap(movieInfo -> {
			Mono<List<Review>> monoReviews = reviewService.retrieveReviewsFlux(movieInfo.getMovieInfoId()).log()
					.collectList();

			return monoReviews.map(reviews -> {
				System.out.println("Created movie for movieInfoId: " + movieInfo.getMovieInfoId());
				return new Movie(movieInfo, reviews);
			});
		})
		.sort((firstMovie, secondMovie) -> {
			return (int) (firstMovie.getMovieInfo().getSortOrder()
					- secondMovie.getMovieInfo().getSortOrder());
		});
	}

	public Mono<Movie> getMovieById(Long movieInfoId) {
		Mono<MovieInfo> movieInfoMono = movieInfoService.retrieveMovieInfoMonoUsingId(movieInfoId);
		Mono<List<Review>> reviewsMono = reviewService.retrieveReviewsFlux(movieInfoId).collectList();

		return movieInfoMono.zipWith(reviewsMono, (movieInfo, reviews) -> {
			return new Movie(movieInfo, reviews);
		});
	}
}
