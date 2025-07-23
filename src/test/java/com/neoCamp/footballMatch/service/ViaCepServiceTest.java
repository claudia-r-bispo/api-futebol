package com.neoCamp.footballMatch.service;

import com.neoCamp.footballMatch.response.ViaCepResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ViaCepServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ViaCepService viaCepService;

    private ViaCepResponse viaCepResponseValida;
    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    @BeforeEach
    void setUp() {

        viaCepResponseValida = new ViaCepResponse();
        viaCepResponseValida.setCep("01310-100");
        viaCepResponseValida.setLogradouro("Avenida Paulista");
        viaCepResponseValida.setBairro("Bela Vista");
        viaCepResponseValida.setCidade("São Paulo");
        viaCepResponseValida.setEstado("SP");


        try {
            java.lang.reflect.Field field = ViaCepService.class.getDeclaredField("restTemplate");
            field.setAccessible(true);
            field.set(viaCepService, restTemplate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void buscarCep_ComCepValido_DeveRetornarViaCepResponse() {

        String cep = "01310-100";
        String cepLimpo = "01310100";

        when(restTemplate.getForObject(VIA_CEP_URL, ViaCepResponse.class, cepLimpo))
                .thenReturn(viaCepResponseValida);


        ViaCepResponse resultado = viaCepService.buscarCep(cep);


        assertNotNull(resultado);
        assertEquals("01310-100", resultado.getCep());
        assertEquals("Avenida Paulista", resultado.getLogradouro());
        assertEquals("Bela Vista", resultado.getBairro());
        assertEquals("São Paulo", resultado.getCidade());
        assertEquals("SP", resultado.getEstado());

        verify(restTemplate).getForObject(VIA_CEP_URL, ViaCepResponse.class, cepLimpo);
    }

    @Test
    void buscarCep_ComCepSemFormatacao_DeveRemoverCaracteresEspeciais() {

        String cepComFormatacao = "01310-100";
        String cepLimpo = "01310100";

        when(restTemplate.getForObject(VIA_CEP_URL, ViaCepResponse.class, cepLimpo))
                .thenReturn(viaCepResponseValida);


        ViaCepResponse resultado = viaCepService.buscarCep(cepComFormatacao);


        assertNotNull(resultado);
        verify(restTemplate).getForObject(VIA_CEP_URL, ViaCepResponse.class, cepLimpo);
    }

    @Test
    void buscarCep_ComCepMenorQue8Digitos_DeveLancarIllegalArgumentException() {

        String cepInvalido = "1234567";


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> viaCepService.buscarCep(cepInvalido)
        );

        assertEquals("CEP deve conter 8 dígitos", exception.getMessage());
        verify(restTemplate, never()).getForObject(anyString(), any(), anyString());
    }

    @Test
    void buscarCep_ComCepMaiorQue8Digitos_DeveLancarIllegalArgumentException() {

        String cepInvalido = "123456789";


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> viaCepService.buscarCep(cepInvalido)
        );

        assertEquals("CEP deve conter 8 dígitos", exception.getMessage());
        verify(restTemplate, never()).getForObject(anyString(), any(), anyString());
    }

    @Test
    void buscarCep_ComCepVazio_DeveLancarIllegalArgumentException() {

        String cepVazio = "";


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> viaCepService.buscarCep(cepVazio)
        );

        assertEquals("CEP deve conter 8 dígitos", exception.getMessage());
    }

    @Test
    void buscarCep_ComResponseNull_DeveLancarIllegalArgumentException() {

        String cep = "01310100";

        when(restTemplate.getForObject(VIA_CEP_URL, ViaCepResponse.class, cep))
                .thenReturn(null);


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> viaCepService.buscarCep(cep)
        );

        assertEquals("CEP não encontrado ou não possui logradouro válido", exception.getMessage());
    }

    @Test
    void buscarCep_ComLogradouroNull_DeveLancarIllegalArgumentException() {

        String cep = "01310100";
        ViaCepResponse responseComLogradouroNull = new ViaCepResponse();
        responseComLogradouroNull.setLogradouro(null);

        when(restTemplate.getForObject(VIA_CEP_URL, ViaCepResponse.class, cep))
                .thenReturn(responseComLogradouroNull);


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> viaCepService.buscarCep(cep)
        );

        assertEquals("CEP não encontrado ou não possui logradouro válido", exception.getMessage());
    }

    @Test
    void buscarCep_ComLogradouroVazio_DeveLancarIllegalArgumentException() {

        String cep = "01310100";
        ViaCepResponse responseComLogradouroVazio = new ViaCepResponse();
        responseComLogradouroVazio.setLogradouro("   ");

        when(restTemplate.getForObject(VIA_CEP_URL, ViaCepResponse.class, cep))
                .thenReturn(responseComLogradouroVazio);


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> viaCepService.buscarCep(cep)
        );

        assertEquals("CEP não encontrado ou não possui logradouro válido", exception.getMessage());
    }

    @Test
    void buscarCep_ComRestClientException_DeveLancarRuntimeException() {

        String cep = "01310100";
        String mensagemErro = "Erro de conexão";

        when(restTemplate.getForObject(VIA_CEP_URL, ViaCepResponse.class, cep))
                .thenThrow(new RestClientException(mensagemErro));


        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> viaCepService.buscarCep(cep)
        );

        assertEquals("Erro ao consultar CEP na API ViaCEP: " + mensagemErro, exception.getMessage());
    }

    @Test
    void buscarCep_ComCepApenasNumeros_DeveProcessarCorretamente() {

        String cep = "01310100";

        when(restTemplate.getForObject(VIA_CEP_URL, ViaCepResponse.class, cep))
                .thenReturn(viaCepResponseValida);


        ViaCepResponse resultado = viaCepService.buscarCep(cep);


        assertNotNull(resultado);
        assertEquals("Avenida Paulista", resultado.getLogradouro());
    }

    @Test
    void buscarCep_ComCaracteresEspeciais_DeveRemoverCaracteres() {

        String cepComCaracteres = "01.310-100";
        String cepLimpo = "01310100";

        when(restTemplate.getForObject(VIA_CEP_URL, ViaCepResponse.class, cepLimpo))
                .thenReturn(viaCepResponseValida);


        ViaCepResponse resultado = viaCepService.buscarCep(cepComCaracteres);


        assertNotNull(resultado);
        verify(restTemplate).getForObject(VIA_CEP_URL, ViaCepResponse.class, cepLimpo);
    }
}
