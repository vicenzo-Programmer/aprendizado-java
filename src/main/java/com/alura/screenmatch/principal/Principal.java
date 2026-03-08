package com.alura.screenmatch.principal;

import com.alura.screenmatch.model.DadosEp;
import com.alura.screenmatch.model.DadosTemporada;
import com.alura.screenmatch.model.Episodio;
import com.alura.screenmatch.model.dadosSerie;
import com.alura.screenmatch.system.ConsumoApi;
import com.alura.screenmatch.system.converteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static Scanner scanner = new Scanner(System.in);
    private static ConsumoApi consumo = new ConsumoApi();
    private final static String ENDERECO = "https://www.omdbapi.com/?t=";
    private final static  String API_KEY = "&apikey=aace5842";

    public void exibmenu() {

        System.out.println("Digite o nome de uma serie");
        var nomeSerie = scanner.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        List<DadosTemporada> temporadas = new ArrayList<>();
        converteDados conversor = new converteDados();

        dadosSerie dados = conversor.obterdados(json, dadosSerie.class);
        System.out.println(dados);
        for (int i = 1; i < dados.temporadas(); i++) {
            json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterdados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        List<DadosEp> dadosEps = (List<DadosEp>) temporadas.stream().flatMap(t -> t.episodios().stream()).collect(Collectors.toList());


        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());
        System.out.println("\n Top 10 Episodios");
        dadosEps.stream()
                .filter(e -> e.avaliacao() != null && !e.avaliacao().equalsIgnoreCase("N/A")) // 1º Filtra nulos e "N/A"
                .peek(e -> System.out.println("Filtro (n/a)" + e))
                .sorted(Comparator.comparing(DadosEp::avaliacao).reversed()) // 2º Ordena (adicionei reversed para ver as melhores notas primeiro)
                .peek(e -> System.out.println("Sorteamento" + e))
                .map(e -> e.titulo().toUpperCase())
                .peek(e -> System.out.println("Mapeamento" + e))
                .limit(10) // 3º Pega os 10 melhores
                .peek(e -> System.out.println("Limite"+ e))
                .forEach(System.out::println);
        System.out.println("Digite um trecho do titulo do Episodio");
        var trechoTitulo = scanner.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toLowerCase().contains(trechoTitulo))
                .findFirst();
                if(episodioBuscado.isPresent()){
                    System.out.println("Episodio Encontrado!");
                    System.out.println("Temporada : " + episodioBuscado.get().getTemporada());
                }
                else
                {
                    System.out.println("Episodio nao encontrado");
                }

                Map<Integer ,Double> avaliacoesportemporada = episodios.stream()
                        .filter(e -> e.getAvaliacao() > 0.0)
                        .collect(Collectors.groupingBy(Episodio::getTemporada,
                                Collectors.averagingDouble(Episodio::getAvaliacao)));
        System.out.println(avaliacoesportemporada);
      //  System.out.println("Digite a partir de que ano vc quer eps");
       // var ano = scanner.nextInt();
       // scanner.nextLine();

       // LocalDate data = LocalDate.of(ano, 1, 1);

       // DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

     //   episodios.stream()
       //         .filter(e -> e.getData() != null && e.getData().isAfter(data))
       //         .forEach(e -> System.out.println(
       //                 "Temporada: " + e.getTemporada() +
       //                         " Episódio: " + e.getTitulo() +
       //                         " Data lançamento: " + e.getData().format(formatador)
       //         ));

        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        System.out.println("Média: " + est.getAverage());
        System.out.println("Melhor episódio: " + est.getMax());
        System.out.println("Pior episódio: " + est.getMin());
        System.out.println("Quantidade: " + est.getCount());;
    }

    //

}
