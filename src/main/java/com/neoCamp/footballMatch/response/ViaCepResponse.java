package com.neoCamp.footballMatch.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.*;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos não mapeados
public class ViaCepResponse {

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;

    @JsonProperty("localidade")
    private String cidade;

    @JsonProperty("uf")
    private String estado;

    private String regiao;
    private String ibge;
    private String ddd;
    private String siafi;


    public ViaCepResponse() {}


    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getRegiao() { return regiao; }
    public void setRegiao(String regiao) { this.regiao = regiao; }

    public String getIbge() { return ibge; }
    public void setIbge(String ibge) { this.ibge = ibge; }

    public String getDdd() { return ddd; }
    public void setDdd(String ddd) { this.ddd = ddd; }

    public String getSiafi() { return siafi; }
    public void setSiafi(String siafi) { this.siafi = siafi; }


    public boolean isValidAddress() {
        return cep != null && !cep.trim().isEmpty() &&
                logradouro != null && !logradouro.trim().isEmpty();
    }

    public boolean isValidStateForFootball() {
        if (estado == null) return false;

        Set<String> estadosComFutebol = Set.of(
                "SP", "RJ", "MG", "RS", "PR", "SC", "BA", "PE", "CE", "GO",
                "DF", "MT", "MS", "ES", "PB", "RN", "AL", "SE", "PI", "MA",
                "PA", "AM", "AC", "RO", "RR", "AP", "TO"
        );

        return estadosComFutebol.contains(estado.toUpperCase());
    }

    public boolean isValidCityForStadium() {
        if (cidade == null) return false;

        Set<String> cidadesComEstadios = Set.of(
                "São Paulo", "Rio de Janeiro", "Belo Horizonte", "Porto Alegre",
                "Curitiba", "Salvador", "Recife", "Fortaleza", "Brasília",
                "Goiânia", "Cuiabá", "Manaus", "Belém", "Santos", "Campinas",
                "Florianópolis", "Vitória", "Maceió", "João Pessoa", "Natal",
                "Aracaju", "São Luís", "Teresina", "Campo Grande"
        );

        return cidadesComEstadios.contains(cidade);
    }

    public boolean isCityWithSerieAStadium() {
        if (cidade == null) return false;

        Set<String> cidadesSerieA = Set.of(
                "São Paulo", "Rio de Janeiro", "Belo Horizonte", "Porto Alegre",
                "Curitiba", "Salvador", "Recife", "Fortaleza", "Brasília",
                "Goiânia", "Cuiabá", "Santos", "Campinas", "Florianópolis"
        );

        return cidadesSerieA.contains(cidade);
    }

    public boolean isRegionConsistentWithState() {
        if (estado == null || regiao == null) {
            return false;
        }


        Map<String, String> estadoParaRegiao = new HashMap<>();

        // Norte
        estadoParaRegiao.put("AC", "Norte");
        estadoParaRegiao.put("AP", "Norte");
        estadoParaRegiao.put("AM", "Norte");
        estadoParaRegiao.put("PA", "Norte");
        estadoParaRegiao.put("RO", "Norte");
        estadoParaRegiao.put("RR", "Norte");
        estadoParaRegiao.put("TO", "Norte");

        // Nordeste
        estadoParaRegiao.put("AL", "Nordeste");
        estadoParaRegiao.put("BA", "Nordeste");
        estadoParaRegiao.put("CE", "Nordeste");
        estadoParaRegiao.put("MA", "Nordeste");
        estadoParaRegiao.put("PB", "Nordeste");
        estadoParaRegiao.put("PE", "Nordeste");
        estadoParaRegiao.put("PI", "Nordeste");
        estadoParaRegiao.put("RN", "Nordeste");
        estadoParaRegiao.put("SE", "Nordeste");

        // Centro-Oeste
        estadoParaRegiao.put("GO", "Centro-Oeste");
        estadoParaRegiao.put("MT", "Centro-Oeste");
        estadoParaRegiao.put("MS", "Centro-Oeste");
        estadoParaRegiao.put("DF", "Centro-Oeste");

        // Sudeste
        estadoParaRegiao.put("ES", "Sudeste");
        estadoParaRegiao.put("MG", "Sudeste");
        estadoParaRegiao.put("RJ", "Sudeste");
        estadoParaRegiao.put("SP", "Sudeste");

        // Sul
        estadoParaRegiao.put("PR", "Sul");
        estadoParaRegiao.put("RS", "Sul");
        estadoParaRegiao.put("SC", "Sul");

        String regiaoCorreta = estadoParaRegiao.get(estado.toUpperCase());
        return regiaoCorreta != null && regiaoCorreta.equalsIgnoreCase(regiao);
    }

    public boolean isValidForStadium() {
        return isValidAddress() &&
                isValidStateForFootball() &&
                isValidCityForStadium() &&
                isRegionConsistentWithState();
    }

    public int getStadiumSuitabilityScore() {
        int score = 0;


        if (isValidAddress()) {
            score += 30;
        }

        if (isValidStateForFootball()) {
            score += 25;
        }


        if (isValidCityForStadium()) {
            score += 20;
        }


        if (isRegionConsistentWithState()) {
            score += 15;
        }


        if (isCityWithSerieAStadium()) {
            score += 10;
        }

        return score;
    }

    public String getValidationMessage() {
        if (getStadiumSuitabilityScore() == 100) {
            return "Excelente localização para estádio de futebol!";
        } else if (!isValidStateForFootball()) {
            return "Estado com pouca tradição no futebol profissional.";
        } else if (!isValidCityForStadium()) {
            return "Cidade não reconhecida para estádios profissionais.";
        } else if (!isRegionConsistentWithState()) {
            return "Região inconsistente com o estado informado.";
        } else {
            return "Localização adequada para estádio de futebol.";
        }
    }

    public List<String> getValidCitiesForCurrentState() {
        if (estado == null) return Collections.emptyList();

        Map<String, List<String>> cidadesPorEstado = Map.of(
                "SP", Arrays.asList("São Paulo", "Santos", "Campinas", "Ribeirão Preto"),
                "RJ", Arrays.asList("Rio de Janeiro", "Niterói", "Nova Iguaçu"),
                "MG", Arrays.asList("Belo Horizonte", "Uberlândia", "Juiz de Fora"),
                "RS", Arrays.asList("Porto Alegre", "Caxias do Sul", "Pelotas"),
                "PR", Arrays.asList("Curitiba", "Londrina", "Maringá")
        );

        return cidadesPorEstado.getOrDefault(estado.toUpperCase(), Collections.emptyList());
    }
}