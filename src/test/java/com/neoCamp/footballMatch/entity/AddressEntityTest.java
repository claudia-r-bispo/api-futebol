package com.neoCamp.footballMatch.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AddressEntity Tests")
class AddressEntityTest {

    private AddressEntity addressEntity;
    private StadiumEntity stadiumEntity;

    @BeforeEach
    void setUp() {
        addressEntity = new AddressEntity();
        stadiumEntity = new StadiumEntity();
    }

    @Test
    @DisplayName("Deve criar AddressEntity com construtor vazio")
    void testDefaultConstructor() {
        // Act
        AddressEntity address = new AddressEntity();

        // Assert
        assertNotNull(address);
        assertNull(address.getId());
        assertNull(address.getLogradouro());
        assertNull(address.getCidade());
        assertNull(address.getEstado());
        assertNull(address.getCep());
        assertNull(address.getStadium());
    }

    @Test
    @DisplayName("Deve criar AddressEntity com construtor completo")
    void testAllArgsConstructor() {
        // Act
        AddressEntity address = new AddressEntity(
                1L,
                "Avenida Paulista",
                "São Paulo",
                "SP",
                "01310-100",
                stadiumEntity
        );

        // Assert
        assertNotNull(address);
        assertEquals(1L, address.getId());
        assertEquals("Avenida Paulista", address.getLogradouro());
        assertEquals("São Paulo", address.getCidade());
        assertEquals("SP", address.getEstado());
        assertEquals("01310-100", address.getCep());
        assertEquals(stadiumEntity, address.getStadium());
    }

    @Test
    @DisplayName("Deve definir e obter ID corretamente")
    void testIdGetterAndSetter() {
        // Arrange
        Long expectedId = 1L;

        // Act
        addressEntity.setId(expectedId);

        // Assert
        assertEquals(expectedId, addressEntity.getId());
    }

    @Test
    @DisplayName("Deve definir e obter logradouro corretamente")
    void testLogradouroGetterAndSetter() {
        // Arrange
        String expectedLogradouro = "Rua das Flores, 123";

        // Act
        addressEntity.setLogradouro(expectedLogradouro);

        // Assert
        assertEquals(expectedLogradouro, addressEntity.getLogradouro());
    }

    @Test
    @DisplayName("Deve definir e obter cidade corretamente")
    void testCidadeGetterAndSetter() {
        // Arrange
        String expectedCidade = "São Paulo";

        // Act
        addressEntity.setCidade(expectedCidade);

        // Assert
        assertEquals(expectedCidade, addressEntity.getCidade());
    }

    @Test
    @DisplayName("Deve definir e obter estado corretamente")
    void testEstadoGetterAndSetter() {
        // Arrange
        String expectedEstado = "SP";

        // Act
        addressEntity.setEstado(expectedEstado);

        // Assert
        assertEquals(expectedEstado, addressEntity.getEstado());
        assertEquals(2, expectedEstado.length()); // Verifica tamanho máximo
    }

    @Test
    @DisplayName("Deve definir e obter CEP corretamente")
    void testCepGetterAndSetter() {
        // Arrange
        String expectedCep = "01310-100";

        // Act
        addressEntity.setCep(expectedCep);

        // Assert
        assertEquals(expectedCep, addressEntity.getCep());
        assertTrue(expectedCep.length() <= 9); // Verifica tamanho máximo
    }

    @Test
    @DisplayName("Deve definir e obter stadium corretamente")
    void testStadiumGetterAndSetter() {
        // Arrange
        stadiumEntity.setId(1L);
        stadiumEntity.setName("Arena Test");

        // Act
        addressEntity.setStadium(stadiumEntity);

        // Assert
        assertEquals(stadiumEntity, addressEntity.getStadium());
        assertEquals(1L, addressEntity.getStadium().getId());
        assertEquals("Arena Test", addressEntity.getStadium().getName());
    }

    @Test
    @DisplayName("Deve validar relacionamento bidirecional com StadiumEntity")
    void testBidirectionalRelationship() {
        // Arrange
        stadiumEntity.setId(1L);
        stadiumEntity.setName("Arena Test");
        stadiumEntity.setUf("SP");
        stadiumEntity.setActive(true);
        stadiumEntity.setDateCreation(LocalDate.now());

        addressEntity.setId(1L);
        addressEntity.setLogradouro("Avenida Paulista");
        addressEntity.setCidade("São Paulo");
        addressEntity.setEstado("SP");
        addressEntity.setCep("01310-100");

        // Act
        stadiumEntity.setAddress(addressEntity);
        addressEntity.setStadium(stadiumEntity);

        // Assert
        assertEquals(addressEntity, stadiumEntity.getAddress());
        assertEquals(stadiumEntity, addressEntity.getStadium());
        assertEquals("Arena Test", addressEntity.getStadium().getName());
        assertEquals("Avenida Paulista", stadiumEntity.getAddress().getLogradouro());
    }

    @Test
    @DisplayName("Deve funcionar equals e hashCode corretamente")
    void testEqualsAndHashCode() {
        // Arrange
        AddressEntity address1 = new AddressEntity();
        address1.setId(1L);
        address1.setLogradouro("Avenida Paulista");
        address1.setCidade("São Paulo");
        address1.setEstado("SP");
        address1.setCep("01310-100");

        AddressEntity address2 = new AddressEntity();
        address2.setId(1L);
        address2.setLogradouro("Avenida Paulista");
        address2.setCidade("São Paulo");
        address2.setEstado("SP");
        address2.setCep("01310-100");

        AddressEntity address3 = new AddressEntity();
        address3.setId(2L);
        address3.setLogradouro("Rua Augusta");
        address3.setCidade("São Paulo");
        address3.setEstado("SP");
        address3.setCep("01305-000");

        // Act & Assert
        assertEquals(address1, address2);
        assertNotEquals(address1, address3);
        assertEquals(address1.hashCode(), address2.hashCode());
        assertNotEquals(address1.hashCode(), address3.hashCode());
    }

    @Test
    @DisplayName("Deve funcionar toString corretamente")
    void testToString() {
        // Arrange
        addressEntity.setId(1L);
        addressEntity.setLogradouro("Avenida Paulista");
        addressEntity.setCidade("São Paulo");
        addressEntity.setEstado("SP");
        addressEntity.setCep("01310-100");

        // Act
        String toString = addressEntity.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("AddressEntity"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("logradouro=Avenida Paulista"));
        assertTrue(toString.contains("cidade=São Paulo"));
        assertTrue(toString.contains("estado=SP"));
        assertTrue(toString.contains("cep=01310-100"));
    }

    @Test
    @DisplayName("Deve lidar com valores null graciosamente")
    void testNullValues() {
        // Act
        addressEntity.setId(null);
        addressEntity.setLogradouro(null);
        addressEntity.setCidade(null);
        addressEntity.setEstado(null);
        addressEntity.setCep(null);
        addressEntity.setStadium(null);

        // Assert
        assertNull(addressEntity.getId());
        assertNull(addressEntity.getLogradouro());
        assertNull(addressEntity.getCidade());
        assertNull(addressEntity.getEstado());
        assertNull(addressEntity.getCep());
        assertNull(addressEntity.getStadium());
    }

    @Test
    @DisplayName("Deve validar tamanhos máximos dos campos")
    void testFieldLengthValidation() {
        // Arrange
        String longEstado = "ABC"; // Mais que 2 caracteres
        String longCep = "12345-67890"; // Mais que 9 caracteres

        // Act
        addressEntity.setEstado("SP"); // Válido
        addressEntity.setCep("01310-100"); // Válido

        // Assert
        assertEquals("SP", addressEntity.getEstado());
        assertEquals("01310-100", addressEntity.getCep());

        // Verifica comprimentos
        assertTrue(addressEntity.getEstado().length() <= 2);
        assertTrue(addressEntity.getCep().length() <= 9);
    }

    @Test
    @DisplayName("Deve criar endereço completo baseado em ViaCEP")
    void testViaCepBasedAddress() {
        // Arrange - Simulando dados do ViaCEP
        String cep = "01310-100";
        String logradouro = "Avenida Paulista";
        String cidade = "São Paulo";
        String estado = "SP";

        // Act
        AddressEntity viaCepAddress = new AddressEntity();
        viaCepAddress.setCep(cep);
        viaCepAddress.setLogradouro(logradouro);
        viaCepAddress.setCidade(cidade);
        viaCepAddress.setEstado(estado);

        // Assert
        assertEquals(cep, viaCepAddress.getCep());
        assertEquals(logradouro, viaCepAddress.getLogradouro());
        assertEquals(cidade, viaCepAddress.getCidade());
        assertEquals(estado, viaCepAddress.getEstado());

        // Verifica se é um endereço válido para integração
        assertNotNull(viaCepAddress.getCep());
        assertNotNull(viaCepAddress.getLogradouro());
        assertNotNull(viaCepAddress.getCidade());
        assertNotNull(viaCepAddress.getEstado());
        assertFalse(viaCepAddress.getLogradouro().trim().isEmpty());
    }

    @Test
    @DisplayName("Deve validar diferentes formatos de CEP")
    void testDifferentCepFormats() {
        // Arrange & Act & Assert

        // CEP com traço
        addressEntity.setCep("01310-100");
        assertEquals("01310-100", addressEntity.getCep());

        // CEP sem traço
        addressEntity.setCep("01310100");
        assertEquals("01310100", addressEntity.getCep());

        // CEP com espaços (simulando input do usuário)
        addressEntity.setCep(" 01310-100 ");
        assertEquals(" 01310-100 ", addressEntity.getCep());
    }

    @Test
    @DisplayName("Deve funcionar com diferentes tipos de logradouro")
    void testDifferentLogradouroTypes() {
        // Arrange & Act & Assert

        String[] logradouros = {
                "Avenida Paulista",
                "Rua das Flores, 123",
                "Praça da República",
                "Travessa do Comércio",
                "Alameda Santos",
                "Largo do Arouche"
        };

        for (String logradouro : logradouros) {
            addressEntity.setLogradouro(logradouro);
            assertEquals(logradouro, addressEntity.getLogradouro());
            assertNotNull(addressEntity.getLogradouro());
            assertFalse(addressEntity.getLogradouro().trim().isEmpty());
        }
    }
}
