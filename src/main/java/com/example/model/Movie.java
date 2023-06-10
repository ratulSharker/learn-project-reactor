package com.example.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

	private MovieInfo movieInfo;
	private List<Review> reviewList;
	private Revenue revenue;

	public Movie(MovieInfo movieInfo, List<Review> reviewList) {
		this.movieInfo = movieInfo;
		this.reviewList = reviewList;
	}

	@Override
	public String toString() {
		return "Movie: " + movieInfo.getMovieInfoId();
	}

}
