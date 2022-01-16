package com.algaworks.algafood.client.api;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import com.algaworks.algafood.client.api.model.RestauranteResumoModel;

public class ListagemRestaurantesMain {

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080";
		
		RestauranteClient restauranteClient = new RestauranteClient(restTemplate, url);
		List<RestauranteResumoModel> restaurantes = restauranteClient.listar();
		restaurantes.stream()
		.forEach(restaurante -> System.out.println(restaurante));
	}
}
