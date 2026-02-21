package com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record dadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("TotalSeasons")Integer temporadas,
                         @JsonAlias("imdbRatings")String nota) {
}
