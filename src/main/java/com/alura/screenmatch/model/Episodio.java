package com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class Episodio {
    private Integer temporada;
    private Integer numero;
    private String titulo;
    private double avaliacao;
    private LocalDate data;

    public Episodio(Integer temporada , DadosEp dadosepisodio) {
        this.titulo = dadosepisodio.titulo();
        this.temporada = temporada;
        this.numero = dadosepisodio.numero();
        try {
            this.avaliacao = Double.valueOf(dadosepisodio.avaliacao());
        } catch (NumberFormatException | NullPointerException e) {
            this.avaliacao = 0.0;
        }
        try {
            // Tenta converter o texto da API (ex: "2023-10-25") para LocalDate
            this.data = LocalDate.parse(dadosepisodio.dataLancamento());
        } catch (DateTimeParseException | NullPointerException e) {
            // Se a data vier errada ou "N/A", definimos como nulo para não quebrar o código
            this.data = null;
        }

    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return
                "temporada=" + temporada +
                ", numero=" + numero +
                ", titulo='" + titulo + '\'' +
                ", avaliacao=" + avaliacao +
                ", data=" + data
                ;
    }
}
