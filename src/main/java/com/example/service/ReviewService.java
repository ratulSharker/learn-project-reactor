package com.example.service;

import java.time.Duration;
import java.util.List;

import com.example.model.Review;

import reactor.core.publisher.Flux;

public class ReviewService {

    public  List<Review> retrieveReviews(long movieInfoId){

        return List.of(new Review(1L, movieInfoId, "Awesome Movie", 8.9),
                new Review(2L, movieInfoId, "Excellent Movie", 9.0));
    }

    public Flux<Review> retrieveReviewsFlux(long movieInfoId) {

		System.out.println("Getting reviews for movieInfoId : " + movieInfoId);
		Long delay = 0L;
		if(movieInfoId == 100L) {
			delay = 5000L;
		} else if(movieInfoId == 101L) {
			delay = 1000L;
		} else {
			delay = 500L;
		}

        var reviewsList = List.of(new Review(1L,movieInfoId, "Awesome Movie", 8.9),
                new Review(2L, movieInfoId, "Excellent Movie", 9.0));
        return Flux.fromIterable(reviewsList).delayElements(Duration.ofMillis(delay));
    }

}
