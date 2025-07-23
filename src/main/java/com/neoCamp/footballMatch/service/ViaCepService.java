package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.response.ViaCepResponse; // ← Import correto
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

@Service
public class ViaCepService {

    private final RestTemplate restTemplate;
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    public ViaCepService() {
        this.restTemplate = new RestTemplate();
    }

    public ViaCepResponse buscarCep(String cep) {
        try {
            String cepLimpo = cep.replaceAll("[^0-9]", "");

            if (cepLimpo.length() != 8) {
                throw new IllegalArgumentException("CEP deve conter 8 dígitos");
            }

            ViaCepResponse response = restTemplate.getForObject(
                    VIA_CEP_URL,
                    ViaCepResponse.class,
                    cepLimpo
            );

            if (response != null && response.getLogradouro() != null && !response.getLogradouro().trim().isEmpty()) {
                return response;
            } else {
                throw new IllegalArgumentException("CEP não encontrado ou não possui logradouro válido");
            }

        } catch (RestClientException e) {
            throw new RuntimeException("Erro ao consultar CEP na API ViaCEP: " + e.getMessage());
        }
    }
}