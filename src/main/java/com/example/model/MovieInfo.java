package com.example.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieInfo {
	private Long movieInfoId;
	private String name;
	private Integer year;
	private List<String> cast;
	private LocalDate release_date;
	private int sortOrder;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "MovieInfo: " + movieInfoId;
	}
}
