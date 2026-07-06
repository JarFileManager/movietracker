package com.jatin.movietracker.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetRandomMovieRequest {

    private List<Integer> genreIds;

    private Integer fromYear;

    private Integer toYear;

    private Double minimumRating;

    private Boolean includeAdult;
}
