package com.alura.screenmatch.system;

import com.alura.screenmatch.model.DadosEp;
import com.alura.screenmatch.model.DadosTemporada;
import com.alura.screenmatch.model.dadosSerie;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoApi = new ConsumoApi();
		var serie = "suits";
		var json = consumoApi.obterDados("http://www.omdbapi.com/?t=suits&Season=1&apikey=aace5842");
		System.out.println(json);
		converteDados conversor = new converteDados();
		dadosSerie dados =  conversor.obterdados(json,dadosSerie.class);
		System.out.println(dados);
		json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season=1&episode=2&apikey=aace5842");
		DadosEp dadosEp = conversor.obterdados(json,DadosEp.class);
		System.out.println(dadosEp);
		List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 0; i < dados.temporadas(); i++) {
			json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&season="+i+"&apikey=aace5842");
			DadosTemporada dadosTemporada = conversor.obterdados(json,DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);
	}
}
