package com.alura.screenmatch.system;

import com.alura.screenmatch.model.dadosSerie;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
	}
}
