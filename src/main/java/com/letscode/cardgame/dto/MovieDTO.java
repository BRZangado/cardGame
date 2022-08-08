package com.letscode.cardgame.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO{
    @JsonProperty("Title")
    private String title;
    private String imdbRating;
    private String imdbVotes;
}
