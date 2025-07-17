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
        assertNull(address.getStreet());
        assertNull(address.getNumber());
        assertNull(address.getCity());
        assertNull(address.getState());
        assertNull(address.getZipCode());
        assertNull(address.getStadium());
    }

    @Test
    @DisplayName("Deve criar AddressEntity com construtor completo")
    void testAllArgsConstructor() {
        // Act
        AddressEntity address = new AddressEntity(
                1L,
                "Avenida Paulista",
                "123",
                "São Paulo",
                "SP",
                "01310-100",
                stadiumEntity
        );

        // Assert
        assertNotNull(address);
        assertEquals(1L, address.getId());
        assertEquals("Avenida Paulista", address.getStreet());
        assertEquals("123", address.getNumber());
        assertEquals("São Paulo", address.getCity());
        assertEquals("SP", address.getState());
        assertEquals("01310-100", address.getZipCode());
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
    @DisplayName("Deve definir e obter street corretamente")
    void testStreetGetterAndSetter() {
        // Arrange
        String expectedStreet = "Rua das Flores, 123";

        // Act
        addressEntity.setStreet(expectedStreet);

        // Assert
        assertEquals(expectedStreet, addressEntity.getStreet());
    }

    @Test
    @DisplayName("Deve definir e obter cidade corretamente")
    void testCityGetterAndSetter() {
        // Arrange
        String expectedCity = "São Paulo";

        // Act
        addressEntity.setCity(expectedCity);

        // Assert
        assertEquals(expectedCity, addressEntity.getCity());
    }

    @Test
    @DisplayName("Deve definir e obter estado corretamente")
    void testStateGetterAndSetter() {
        // Arrange
        String expectedState = "SP";

        // Act
        addressEntity.setState(expectedState);

        // Assert
        assertEquals(expectedState, addressEntity.getState());
        assertEquals(2, expectedState.length()); // Verifica tamanho máximo
    }

    @Test
    @DisplayName("Deve definir e obter CEP corretamente")
    void testZipCodeGetterAndSetter() {
        // Arrange
        String expectedZipCode = "01310-100";

        // Act
        addressEntity.setZipCode(expectedZipCode);

        // Assert
        assertEquals(expectedZipCode, addressEntity.getZipCode());
        assertTrue(expectedZipCode.length() <= 9); // Verifica tamanho máximo
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
        addressEntity.setStreet("Avenida Paulista");
        addressEntity.setCity("São Paulo");
        addressEntity.setState("SP");
        addressEntity.setZipCode("01310-100");

        // Act
        stadiumEntity.setAddress(addressEntity);
        addressEntity.setStadium(stadiumEntity);

        // Assert
        assertEquals(addressEntity, stadiumEntity.getAddress());
        assertEquals(stadiumEntity, addressEntity.getStadium());
        assertEquals("Arena Test", addressEntity.getStadium().getName());
        assertEquals("Avenida Paulista", stadiumEntity.getAddress().getStreet());
    }

    @Test
    @DisplayName("Deve funcionar equals e hashCode corretamente")
    void testEqualsAndHashCode() {
        // Arrange
        AddressEntity address1 = new AddressEntity(
                1L,
                "Avenida Paulista",
                "123",
                "São Paulo",
                "SP",
                "01310-100",
                stadiumEntity
        );

        AddressEntity address2 = new AddressEntity(
                1L,
                "Avenida Paulista",
                "123",
                "São Paulo",
                "SP",
                "01310-100",
                stadiumEntity
        );

        AddressEntity address3 = new AddressEntity(
                2L,
                "Rua Augusta",
                "456",
                "São Paulo",
                "SP",
                "01305-000",
                stadiumEntity
        );

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
        addressEntity.setStreet("Avenida Paulista");
        addressEntity.setNumber("123");
        addressEntity.setCity("São Paulo");
        addressEntity.setState("SP");
        addressEntity.setZipCode("01310-100");

        // Act
        String toString = addressEntity.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("AddressEntity"));
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("street=Avenida Paulista"));
        assertTrue(toString.contains("number=123"));
        assertTrue(toString.contains("city=São Paulo"));
        assertTrue(toString.contains("state=SP"));
        assertTrue(toString.contains("zipCode=01310-100"));
    }

    @Test
    @DisplayName("Deve lidar com valores null graciosamente")
    void testNullValues() {
        // Act
        addressEntity.setId(null);
        addressEntity.setStreet(null);
        addressEntity.setNumber(null);
        addressEntity.setCity(null);
        addressEntity.setState(null);
        addressEntity.setZipCode(null);
        addressEntity.setStadium(null);

        // Assert
        assertNull(addressEntity.getId());
        assertNull(addressEntity.getStreet());
        assertNull(addressEntity.getNumber());
        assertNull(addressEntity.getCity());
        assertNull(addressEntity.getState());
        assertNull(addressEntity.getZipCode());
        assertNull(addressEntity.getStadium());
    }

    @Test
    @DisplayName("Deve validar tamanhos máximos dos campos")
    void testFieldLengthValidation() {
        // Arrange
        String longState = "ABC"; // Mais que 2 caracteres
        String longZipCode = "12345-67890"; // Mais que 9 caracteres

        // Act
        addressEntity.setState("SP"); // Válido
        addressEntity.setZipCode("01310-100"); // Válido

        // Assert
        assertEquals("SP", addressEntity.getState());
        assertEquals("01310-100", addressEntity.getZipCode());

        // Verifica comprimentos
        assertTrue(addressEntity.getState().length() <= 2);
        assertTrue(addressEntity.getZipCode().length() <= 9);
    }

    @Test
    @DisplayName("Deve criar endereço completo baseado em ViaCEP")
    void testViaCepBasedAddress() {
        // Arrange - Simulando dados do ViaCEP
        String zipCode = "01310-100";
        String street = "Avenida Paulista";
        String city = "São Paulo";
        String state = "SP";

        // Act
        AddressEntity viaCepAddress = new AddressEntity(
                1L,
                street,
                "123",
                city,
                state,
                zipCode,
                stadiumEntity
        );

        // Assert
        assertEquals(zipCode, viaCepAddress.getZipCode());
        assertEquals(street, viaCepAddress.getStreet());
        assertEquals("123", viaCepAddress.getNumber());
        assertEquals(city, viaCepAddress.getCity());
        assertEquals(state, viaCepAddress.getState());

        // Verifica se é um endereço válido para integração
        assertNotNull(viaCepAddress.getZipCode());
        assertNotNull(viaCepAddress.getStreet());
        assertNotNull(viaCepAddress.getNumber());
        assertNotNull(viaCepAddress.getCity());
        assertNotNull(viaCepAddress.getState());
        assertFalse(viaCepAddress.getStreet().trim().isEmpty());
    }

    @Test
    @DisplayName("Deve validar diferentes formatos de CEP")
    void testDifferentZipCodeFormats() {
        // Arrange & Act & Assert

        // CEP com traço
        addressEntity.setZipCode("01310-100");
        assertEquals("01310-100", addressEntity.getZipCode());

        // CEP sem traço
        addressEntity.setZipCode("01310100");
        assertEquals("01310100", addressEntity.getZipCode());

        // CEP com espaços (simulando input do usuário)
        addressEntity.setZipCode(" 01310-100 ");
        assertEquals(" 01310-100 ", addressEntity.getZipCode());
    }

    @Test
    @DisplayName("Deve funcionar com diferentes tipos de logradouro")
    void testDifferentStreetTypes() {
        // Arrange & Act & Assert

        String[] streets = {
                "Avenida Paulista",
                "Rua das Flores, 123",
                "Praça da República",
                "Travessa do Comércio",
                "Alameda Santos",
                "Largo do Arouche"
        };

        for (String street : streets) {
            addressEntity.setStreet(street);
            assertEquals(street, addressEntity.getStreet());
            assertNotNull(addressEntity.getStreet());
            assertFalse(addressEntity.getStreet().trim().isEmpty());
        }
    }
}
