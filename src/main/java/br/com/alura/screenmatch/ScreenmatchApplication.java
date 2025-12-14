package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
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
		System.out.println("Primeiro projeto spring sem WEB!");
		ConsumoApi consumoApi = new ConsumoApi();
		ConverteDados conversor = new ConverteDados();

		var jsonSerie = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=68d3e477");
		DadosSerie dadosSerie = conversor.obterDados(jsonSerie, DadosSerie.class);
		System.out.println("===========SERIE===========");
		System.out.println(dadosSerie);

		List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++){
			var jsonTemporada = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&Season=" + i + "&&apikey=68d3e477");
            DadosTemporada dadosTemporada = conversor.obterDados(jsonTemporada, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		System.out.println("===========TEMPORADAS===========");
		temporadas.forEach(System.out::println);

		System.out.println("===========EPISODIOS===========");
		var jsonEpisodio = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&Season=1&episode=2&apikey=68d3e477");
		DadosEpisodio  dadosEpisodio = conversor.obterDados(jsonEpisodio, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

	}
}
