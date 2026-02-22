package com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEp(@JsonAlias("Title") String titulo,@JsonAlias("Episode") Integer numero,@JsonAlias("imdbRatings") String avaliacao) {
}
