package com.neoCamp.footballMatch.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ViaCepResponse Football Validation Tests")
class ViaCepResponseTest {

    private ViaCepResponse viaCepResponse;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        viaCepResponse = new ViaCepResponse();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Deve validar estádio em São Paulo - Série A")
    void testValidStadiumSaoPauloSerieA() throws JsonProcessingException {
        // Arrange - Morumbi, São Paulo
        String viaCepJson = """
            {
              "cep": "05650-000",
              "logradouro": "Praça Roberto Gomes Pedrosa",
              "bairro": "Morumbi",
              "localidade": "São Paulo",
              "uf": "SP",
              "regiao": "Sudeste"
            }
            """;

        // Act
        ViaCepResponse response = objectMapper.readValue(viaCepJson, ViaCepResponse.class);

        // Assert
        assertTrue(response.isValidForStadium());
        assertTrue(response.isValidStateForFootball());
        assertTrue(response.isValidCityForStadium());
        assertTrue(response.isCityWithSerieAStadium());
        assertTrue(response.isRegionConsistentWithState());
        assertEquals(100, response.getStadiumSuitabilityScore());
        assertTrue(response.getValidationMessage().contains("Excelente localização"));
    }

    @Test
    @DisplayName("Deve validar estádio no Rio de Janeiro - Maracanã")
    void testValidStadiumRioDeJaneiroMaracana() throws JsonProcessingException {
        // Arrange - Maracanã, Rio de Janeiro
        String viaCepJson = """
            {
              "cep": "20271-110",
              "logradouro": "Rua Professor Eurico Rabelo",
              "bairro": "Maracanã",
              "localidade": "Rio de Janeiro",
              "uf": "RJ",
              "regiao": "Sudeste"
            }
            """;

        // Act
        ViaCepResponse response = objectMapper.readValue(viaCepJson, ViaCepResponse.class);

        // Assert
        assertTrue(response.isValidForStadium());
        assertTrue(response.isCityWithSerieAStadium());
        assertEquals(100, response.getStadiumSuitabilityScore());
    }

    @Test
    @DisplayName("Deve validar estádio em Porto Alegre - Arena do Grêmio")
    void testValidStadiumPortoAlegre() {
        // Arrange
        viaCepResponse.setCep("91330-001");
        viaCepResponse.setLogradouro("Avenida Padre Leopoldo Brentano");
        viaCepResponse.setCidade("Porto Alegre");
        viaCepResponse.setEstado("RS");
        viaCepResponse.setRegiao("Sul");

        // Assert
        assertTrue(viaCepResponse.isValidForStadium());
        assertTrue(viaCepResponse.isValidStateForFootball());
        assertTrue(viaCepResponse.isCityWithSerieAStadium());
        assertEquals(100, viaCepResponse.getStadiumSuitabilityScore());
    }

    @Test
    @DisplayName("Deve identificar cidade inválida para estádio")
    void testInvalidCityForStadium() {
        // Arrange - Cidade pequena sem tradição no futebol
        viaCepResponse.setCep("12345-678");
        viaCepResponse.setLogradouro("Rua Qualquer");
        viaCepResponse.setCidade("Cidade Pequena Inexistente");
        viaCepResponse.setEstado("SP");
        viaCepResponse.setRegiao("Sudeste");

        // Assert
        assertFalse(viaCepResponse.isValidCityForStadium());
        assertFalse(viaCepResponse.isValidForStadium());
        assertTrue(viaCepResponse.getStadiumSuitabilityScore() < 100);
        assertTrue(viaCepResponse.getValidationMessage().contains("não reconhecida"));
    }

    @Test
    @DisplayName("Deve identificar estado sem tradição no futebol")
    void testStateWithoutFootballTradition() {
        // Arrange - Estado hipotético sem futebol profissional
        viaCepResponse.setCep("12345-678");
        viaCepResponse.setLogradouro("Rua Qualquer");
        viaCepResponse.setCidade("Qualquer Cidade");
        viaCepResponse.setEstado("XX"); // Estado inválido
        viaCepResponse.setRegiao("Qualquer");

        // Assert
        assertFalse(viaCepResponse.isValidStateForFootball());
        assertFalse(viaCepResponse.isValidForStadium());
        assertTrue(viaCepResponse.getValidationMessage().contains("pouca tradição"));
    }

    @Test
    @DisplayName("Deve validar região inconsistente com estado")
    void testInconsistentRegion() {
        // Arrange - São Paulo com região errada
        viaCepResponse.setCep("01310-100");
        viaCepResponse.setLogradouro("Avenida Paulista");
        viaCepResponse.setCidade("São Paulo");
        viaCepResponse.setEstado("SP");
        viaCepResponse.setRegiao("Norte"); // Região errada para SP

        // Assert
        assertTrue(viaCepResponse.isValidAddress());
        assertTrue(viaCepResponse.isValidStateForFootball());
        assertFalse(viaCepResponse.isRegionConsistentWithState());
        assertTrue(viaCepResponse.getValidationMessage().contains("inconsistente"));
    }

    @Test
    @DisplayName("Deve listar cidades válidas para estado")
    void testValidCitiesForState() {
        // Arrange
        viaCepResponse.setEstado("SP");

        // Act
        var cidadesValidas = viaCepResponse.getValidCitiesForCurrentState();

        // Assert
        assertFalse(cidadesValidas.isEmpty());
        assertTrue(cidadesValidas.contains("São Paulo"));
        assertTrue(cidadesValidas.contains("Santos"));
        assertTrue(cidadesValidas.contains("Campinas"));
    }

    @Test
    @DisplayName("Deve calcular score de adequação para estádio")
    void testStadiumSuitabilityScore() {
        // Arrange - Teste com diferentes cenários

        // Cenário 1: Endereço completo e válido (100 pontos)
        viaCepResponse.setCep("01310-100");
        viaCepResponse.setLogradouro("Avenida Paulista");
        viaCepResponse.setCidade("São Paulo");
        viaCepResponse.setEstado("SP");
        viaCepResponse.setRegiao("Sudeste");
        assertEquals(100, viaCepResponse.getStadiumSuitabilityScore());

        // Cenário 2: Endereço válido mas cidade não reconhecida
        viaCepResponse.setCidade("Cidade Desconhecida");
        int score2 = viaCepResponse.getStadiumSuitabilityScore();
        assertTrue(score2 >= 55 && score2 < 100); // 30 + 25 = 55 pontos base

        // Cenário 3: Apenas endereço básico válido
        viaCepResponse.setEstado("XX"); // Estado inválido
        assertEquals(30, viaCepResponse.getStadiumSuitabilityScore());
    }

    @Test
    @DisplayName("Deve validar estádios em diferentes regiões do Brasil")
    void testStadiumsInDifferentRegions() {
        // Nordeste - Arena Castelão (Fortaleza)
        viaCepResponse.setCep("60455-000");
        viaCepResponse.setLogradouro("Avenida Alberto Craveiro");
        viaCepResponse.setCidade("Fortaleza");
        viaCepResponse.setEstado("CE");
        viaCepResponse.setRegiao("Nordeste");
        assertTrue(viaCepResponse.isValidForStadium());
        assertTrue(viaCepResponse.isCityWithSerieAStadium());

        // Centro-Oeste - Arena Pantanal (Cuiabá)
        viaCepResponse.setCidade("Cuiabá");
        viaCepResponse.setEstado("MT");
        viaCepResponse.setRegiao("Centro-Oeste");
        assertTrue(viaCepResponse.isValidForStadium());

        // Norte - Arena da Amazônia (Manaus)
        viaCepResponse.setCidade("Manaus");
        viaCepResponse.setEstado("AM");
        viaCepResponse.setRegiao("Norte");
        assertTrue(viaCepResponse.isValidForStadium());
    }

    @Test
    @DisplayName("Deve deserializar JSON real e validar para futebol")
    void testRealJsonWithFootballValidation() throws JsonProcessingException {
        // Arrange - JSON real do ViaCEP para estádio conhecido
        String jsonRealEstadio = """
            {
              "cep": "01310-100",
              "logradouro": "Avenida Paulista",
              "complemento": "",
              "bairro": "Bela Vista",
              "localidade": "São Paulo",
              "uf": "SP",
              "regiao": "Sudeste",
              "ibge": "3550308",
              "ddd": "11",
              "siafi": "7107"
            }
            """;

        // Act
        ViaCepResponse response = objectMapper.readValue(jsonRealEstadio, ViaCepResponse.class);

        // Assert
        assertTrue(response.isValidForStadium());
        assertEquals(100, response.getStadiumSuitabilityScore());
        assertTrue(response.getValidationMessage().contains("Excelente"));
    }
}