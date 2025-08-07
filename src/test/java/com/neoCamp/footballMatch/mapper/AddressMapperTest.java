package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.AddressDTO;
import com.neoCamp.footballMatch.entity.AddressEntity;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AddressMapper Tests")
class AddressMapperTest {
    private UUID testId1;
    private UUID testId2;
    private UUID testStadiumId1;
    
    private AddressDTO addressDTO;
    private AddressEntity addressEntity;

    @BeforeEach
    void setUp() {
        // Initialize test UUIDs
        testId1 = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        testId2 = UUID.fromString("223e4567-e89b-12d3-a456-426614174001");
        testStadiumId1 = UUID.fromString("323e4567-e89b-12d3-a456-426614174002");

        // Setup DTO
        addressDTO = new AddressDTO();
        addressDTO.setId(testId1);
        addressDTO.setLogradouro("Avenida Paulista");
        addressDTO.setCidade("São Paulo");
        addressDTO.setEstado("SP");
        addressDTO.setCep("01310-100");

        // Setup Entity
        addressEntity = new AddressEntity();
        addressEntity.setId(testId1);
        addressEntity.setStreet("Avenida Paulista");
        addressEntity.setCity("São Paulo");
        addressEntity.setState("SP");
        addressEntity.setZipCode("01310-100");
    }

    @Test
    @DisplayName("Deve converter AddressDTO para AddressEntity corretamente")
    void testToEntity_Success() {

        AddressEntity result = AddressMapper.toEntity(addressDTO);


        assertNotNull(result);
        assertEquals(addressDTO.getId(), result.getId());
        assertEquals(addressDTO.getLogradouro(), result.getStreet());
        assertEquals(addressDTO.getCidade(), result.getCity());
        assertEquals(addressDTO.getEstado(), result.getState());
        assertEquals(addressDTO.getCep(), result.getZipCode());
        assertNull(result.getStadium()); // Stadium não é mapeado no toEntity
    }

    @Test
    @DisplayName("Deve retornar null quando AddressDTO for null no toEntity")
    void testToEntity_NullDto_ReturnsNull() {

        AddressEntity result = AddressMapper.toEntity(null);


        assertNull(result);
    }

    @Test
    @DisplayName("Deve converter AddressEntity para AddressDTO corretamente")
    void testToDto_Success() {

        AddressDTO result = AddressMapper.toDto(addressEntity);


        assertNotNull(result);
        assertEquals(addressEntity.getId(), result.getId());
        assertEquals(addressEntity.getStreet(), result.getLogradouro());
        assertEquals(addressEntity.getCity(), result.getCidade());
        assertEquals(addressEntity.getState(), result.getEstado());
        assertEquals(addressEntity.getZipCode(), result.getCep());
    }

    @Test
    @DisplayName("Deve retornar null quando AddressEntity for null no toDto")
    void testToDto_NullEntity_ReturnsNull() {

        AddressDTO result = AddressMapper.toDto(null);


        assertNull(result);
    }

    @Test
    @DisplayName("Deve lidar com campos null no AddressDTO")
    void testToEntity_WithNullFields() {

        AddressDTO dtoWithNulls = new AddressDTO();
        dtoWithNulls.setId(null);
        dtoWithNulls.setLogradouro(null);
        dtoWithNulls.setCidade(null);
        dtoWithNulls.setEstado(null);
        dtoWithNulls.setCep(null);


        AddressEntity result = AddressMapper.toEntity(dtoWithNulls);


        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getStreet());
        assertNull(result.getCity());
        assertNull(result.getState());
        assertNull(result.getZipCode());
    }

    @Test
    @DisplayName("Deve lidar com campos null no AddressEntity")
    void testToDto_WithNullFields() {

        AddressEntity entityWithNulls = new AddressEntity();
        entityWithNulls.setId(null);
        entityWithNulls.setStreet(null);
        entityWithNulls.setCity(null);
        entityWithNulls.setState(null);
        entityWithNulls.setZipCode(null);


        AddressDTO result = AddressMapper.toDto(entityWithNulls);


        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getLogradouro());
        assertNull(result.getCidade());
        assertNull(result.getEstado());
        assertNull(result.getCep());
    }

    @Test
    @DisplayName("Deve converter dados do ViaCEP corretamente")
    void testToEntity_ViaCepData() {

        AddressDTO viaCepDto = new AddressDTO();
        viaCepDto.setLogradouro("Avenida Paulista");
        viaCepDto.setCidade("São Paulo");
        viaCepDto.setEstado("SP");
        viaCepDto.setCep("01310-100");



        AddressEntity result = AddressMapper.toEntity(viaCepDto);


        assertNotNull(result);
        assertNull(result.getId()); // ID deve ser null pois será gerado pelo banco
        assertEquals("Avenida Paulista", result.getStreet());
        assertEquals("São Paulo", result.getCity());
        assertEquals("SP", result.getState());
        assertEquals("01310-100", result.getZipCode());
    }

    @Test
    @DisplayName("Deve manter independência entre objetos após mapeamento")
    void testToEntity_ObjectIndependence() {

        AddressEntity result = AddressMapper.toEntity(addressDTO);


        addressDTO.setLogradouro("Rua Augusta");
        addressDTO.setCidade("Rio de Janeiro");


        assertNotEquals(addressDTO.getLogradouro(), result.getStreet());
        assertNotEquals(addressDTO.getCidade(), result.getCity());
        assertEquals("Avenida Paulista", result.getStreet()); // Valor original
        assertEquals("São Paulo", result.getCity()); // Valor original
    }

    @Test
    @DisplayName("Deve manter independência entre objetos após mapeamento toDto")
    void testToDto_ObjectIndependence() {

        AddressDTO result = AddressMapper.toDto(addressEntity);


        addressEntity.setStreet("Rua Augusta");
        addressEntity.setCity("Rio de Janeiro");


        assertNotEquals(addressEntity.getStreet(), result.getLogradouro());
        assertNotEquals(addressEntity.getCity(), result.getCidade());
        assertEquals("Avenida Paulista", result.getLogradouro()); // Valor original
        assertEquals("São Paulo", result.getCidade()); // Valor original
    }

    @Test
    @DisplayName("Deve converter diferentes tipos de logradouro")
    void testToEntity_DifferentLogradouroTypes() {

        String[] logradouros = {
                "Avenida Paulista",
                "Rua das Flores, 123",
                "Praça da República",
                "Travessa do Comércio, nº 45",
                "Alameda Santos",
                "",
                " "
        };

        for (String logradouro : logradouros) {
            addressDTO.setLogradouro(logradouro);


            AddressEntity result = AddressMapper.toEntity(addressDTO);


            assertEquals(logradouro, result.getStreet());
        }
    }

    @Test
    @DisplayName("Deve converter diferentes formatos de CEP")
    void testToEntity_DifferentCepFormats() {

        String[] ceps = {
                "01310-100",
                "01310100",
                "12345-678",
                "87654321",
                "",
                " 01310-100 "
        };

        for (String cep : ceps) {
            addressDTO.setCep(cep);


            AddressEntity result = AddressMapper.toEntity(addressDTO);


            assertEquals(cep, result.getZipCode());
        }
    }

    @Test
    @DisplayName("Deve ignorar relacionamento Stadium no mapeamento")
    void testToEntity_IgnoresStadiumRelationship() {
        StadiumEntity stadium = new StadiumEntity();
        stadium.setId(testStadiumId1);
        stadium.setName("Arena Test");

        addressEntity.setStadium(stadium);


        AddressDTO result = AddressMapper.toDto(addressEntity);


        assertNotNull(result);
        assertEquals(addressEntity.getId(), result.getId());
        assertEquals(addressEntity.getStreet(), result.getLogradouro());

    }

    @Test
    @DisplayName("Deve fazer mapeamento bidirecional correto")
    void testBidirectionalMapping() {

        AddressEntity entity = AddressMapper.toEntity(addressDTO);
        AddressDTO resultDto = AddressMapper.toDto(entity);


        assertEquals(addressDTO.getId(), resultDto.getId());
        assertEquals(addressDTO.getLogradouro(), resultDto.getLogradouro());
        assertEquals(addressDTO.getCidade(), resultDto.getCidade());
        assertEquals(addressDTO.getEstado(), resultDto.getEstado());
        assertEquals(addressDTO.getCep(), resultDto.getCep());


        AddressDTO dto = AddressMapper.toDto(addressEntity);
        AddressEntity resultEntity = AddressMapper.toEntity(dto);


        assertEquals(addressEntity.getId(), resultEntity.getId());
        assertEquals(addressEntity.getStreet(), resultEntity.getStreet());
        assertEquals(addressEntity.getCity(), resultEntity.getCity());
        assertEquals(addressEntity.getState(), resultEntity.getState());
        assertEquals(addressEntity.getZipCode(), resultEntity.getZipCode());
    }

    @Test
    @DisplayName("Deve lidar com diferentes UUIDs")
    void testDifferentIdTypes() {
        UUID[] uuids = {
            UUID.fromString("00000000-0000-0000-0000-000000000000"),
            UUID.fromString("11111111-1111-1111-1111-111111111111"),
            UUID.fromString("22222222-2222-2222-2222-222222222222"),
            UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"),
            testId1,
            testId2
        };

        for (UUID id : uuids) {
            addressDTO.setId(id);
            AddressEntity result = AddressMapper.toEntity(addressDTO);
            assertEquals(id, result.getId(), "O ID deve ser o mesmo após o mapeamento");
        }
    }

    @Test
    @DisplayName("Deve preservar espaços em branco nos campos")
    void testWhitespacePreservation() {

        addressDTO.setLogradouro(" Avenida Paulista ");
        addressDTO.setCidade("  São Paulo  ");
        addressDTO.setEstado(" SP ");
        addressDTO.setCep(" 01310-100 ");


        AddressEntity result = AddressMapper.toEntity(addressDTO);


        assertEquals(" Avenida Paulista ", result.getStreet());
        assertEquals("  São Paulo  ", result.getCity());
        assertEquals(" SP ", result.getState());
        assertEquals(" 01310-100 ", result.getZipCode());
    }

    @Test
    @DisplayName("Deve lidar com strings vazias")
    void testEmptyStrings() {

        addressDTO.setLogradouro("");
        addressDTO.setCidade("");
        addressDTO.setEstado("");
        addressDTO.setCep("");


        AddressEntity result = AddressMapper.toEntity(addressDTO);


        assertEquals("", result.getStreet());
        assertEquals("", result.getCity());
        assertEquals("", result.getState());
        assertEquals("", result.getZipCode());
    }

    @Test
    @DisplayName("Deve funcionar com dados reais de endereços brasileiros")
    void testRealBrazilianAddresses() {
        Object[][] realAddresses = {
                {UUID.fromString("10000000-0000-0000-0000-000000000001"), "Avenida Paulista", "São Paulo", "SP", "01310-100"},
                {UUID.fromString("20000000-0000-0000-0000-000000000002"), "Rua Oscar Freire", "São Paulo", "SP", "01426-001"},
                {UUID.fromString("30000000-0000-0000-0000-000000000003"), "Avenida Atlântica", "Rio de Janeiro", "RJ", "22070-000"},
                {UUID.fromString("40000000-0000-0000-0000-000000000004"), "Rua da Assembleia", "Rio de Janeiro", "RJ", "20011-000"},
                {UUID.fromString("50000000-0000-0000-0000-000000000005"), "Avenida Boa Viagem", "Recife", "PE", "51030-000"}
        };

        for (Object[] addressData : realAddresses) {
            AddressDTO dto = new AddressDTO();
            dto.setId((UUID) addressData[0]);
            dto.setLogradouro((String) addressData[1]);
            dto.setCidade((String) addressData[2]);
            dto.setEstado((String) addressData[3]);
            dto.setCep((String) addressData[4]);


            AddressEntity entity = AddressMapper.toEntity(dto);
            AddressDTO resultDto = AddressMapper.toDto(entity);


            assertEquals(dto.getId(), resultDto.getId());
            assertEquals(dto.getLogradouro(), resultDto.getLogradouro());
            assertEquals(dto.getCidade(), resultDto.getCidade());
            assertEquals(dto.getEstado(), resultDto.getEstado());
            assertEquals(dto.getCep(), resultDto.getCep());
        }
    }
}
