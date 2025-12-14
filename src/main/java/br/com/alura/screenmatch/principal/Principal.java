package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private String json;

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&&apikey=68d3e477";

    public void exibeMenu(){
        System.out.println("Sistema de busca de séries.");
        System.out.println("Digite o nome da série: ");
        var inputSerie = leitura.nextLine();
        json = consumoApi.obterDados(ENDERECO + inputSerie.replace(" ", "+") + API_KEY);

        // SERIE
        System.out.println("===========SÉRIE===========");
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        // TEMPORADA
		List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++){
			var jsonTemporada = consumoApi.obterDados(ENDERECO + inputSerie.replace(" ", "+") + "&Season=" + i + "&&apikey=68d3e477");

            DadosTemporada dadosTemporada = conversor.obterDados(jsonTemporada, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		System.out.println("===========TEMPORADAS===========");
		temporadas.forEach(System.out::println);

//		System.out.println("===========EPISODIOS===========");
//		var jsonEpisodio = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&Season=1&episode=2&apikey=68d3e477");
//		DadosEpisodio  dadosEpisodio = conversor.obterDados(jsonEpisodio, DadosEpisodio.class);
//		System.out.println(dadosEpisodio);



    }
}
