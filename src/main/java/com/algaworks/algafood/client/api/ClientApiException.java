package com.algaworks.algafood.client.api;

import org.springframework.web.client.RestClientResponseException;

import com.algaworks.algafood.client.api.model.Problem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientApiException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private Problem problem;
	
	public ClientApiException(String message, RestClientResponseException cause) {
		super(message, cause);
		
		deserializeProblem(cause);
	}

	//a ideia e desserializar a exception lancada pela API
	private void deserializeProblem(RestClientResponseException cause) {
		ObjectMapper mapper = new ObjectMapper();
		//configura para nao falhar quando houver propriedades desconhecidas na desserializacao
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); 
		mapper.registerModule(new JavaTimeModule()); //adiciona modulo capaz de lidar com desserializacao de JavaTime
		mapper.findAndRegisterModules(); //necessario para funcionar a questao do novo modulo
		
		try {
			this.problem = mapper.readValue(cause.getResponseBodyAsString(), Problem.class);
		} catch (JsonProcessingException e) {
			log.warn("Não foi possível desserializar a resposta do problema", e);
		}
	}
	
}
