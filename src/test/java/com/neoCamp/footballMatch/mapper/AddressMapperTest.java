package com.neoCamp.footballMatch.mapper;

import com.neoCamp.footballMatch.dto.AddressDTO;
import com.neoCamp.footballMatch.entity.AddressEntity;
import com.neoCamp.footballMatch.entity.StadiumEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AddressMapper Tests")
class AddressMapperTest {

    private AddressDTO addressDTO;
    private AddressEntity addressEntity;

    @BeforeEach
    void setUp() {
        // Setup AddressDTO
        addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setLogradouro("Avenida Paulista");
        addressDTO.setCidade("São Paulo");
        addressDTO.setEstado("SP");
        addressDTO.setCep("01310-100");

        // Setup AddressEntity
        addressEntity = new AddressEntity();
        addressEntity.setId(1L);
        addressEntity.setLogradouro("Avenida Paulista");
        addressEntity.setCidade("São Paulo");
        addressEntity.setEstado("SP");
        addressEntity.setCep("01310-100");
    }

    @Test
    @DisplayName("Deve converter AddressDTO para AddressEntity corretamente")
    void testToEntity_Success() {
        // Act
        AddressEntity result = AddressMapper.toEntity(addressDTO);

        // Assert
        assertNotNull(result);
        assertEquals(addressDTO.getId(), result.getId());
        assertEquals(addressDTO.getLogradouro(), result.getLogradouro());
        assertEquals(addressDTO.getCidade(), result.getCidade());
        assertEquals(addressDTO.getEstado(), result.getEstado());
        assertEquals(addressDTO.getCep(), result.getCep());
        assertNull(result.getStadium()); // Stadium não é mapeado no toEntity
    }

    @Test
    @DisplayName("Deve retornar null quando AddressDTO for null no toEntity")
    void testToEntity_NullDto_ReturnsNull() {
        // Act
        AddressEntity result = AddressMapper.toEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Deve converter AddressEntity para AddressDTO corretamente")
    void testToDto_Success() {
        // Act
        AddressDTO result = AddressMapper.toDto(addressEntity);

        // Assert
        assertNotNull(result);
        assertEquals(addressEntity.getId(), result.getId());
        assertEquals(addressEntity.getLogradouro(), result.getLogradouro());
        assertEquals(addressEntity.getCidade(), result.getCidade());
        assertEquals(addressEntity.getEstado(), result.getEstado());
        assertEquals(addressEntity.getCep(), result.getCep());
    }

    @Test
    @DisplayName("Deve retornar null quando AddressEntity for null no toDto")
    void testToDto_NullEntity_ReturnsNull() {
        // Act
        AddressDTO result = AddressMapper.toDto(null);

        // Assert
        assertNull(result);
    }

    @Test
    @DisplayName("Deve lidar com campos null no AddressDTO")
    void testToEntity_WithNullFields() {
        // Arrange
        AddressDTO dtoWithNulls = new AddressDTO();
        dtoWithNulls.setId(null);
        dtoWithNulls.setLogradouro(null);
        dtoWithNulls.setCidade(null);
        dtoWithNulls.setEstado(null);
        dtoWithNulls.setCep(null);

        // Act
        AddressEntity result = AddressMapper.toEntity(dtoWithNulls);

        // Assert
        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getLogradouro());
        assertNull(result.getCidade());
        assertNull(result.getEstado());
        assertNull(result.getCep());
    }

    @Test
    @DisplayName("Deve lidar com campos null no AddressEntity")
    void testToDto_WithNullFields() {
        // Arrange
        AddressEntity entityWithNulls = new AddressEntity();
        entityWithNulls.setId(null);
        entityWithNulls.setLogradouro(null);
        entityWithNulls.setCidade(null);
        entityWithNulls.setEstado(null);
        entityWithNulls.setCep(null);

        // Act
        AddressDTO result = AddressMapper.toDto(entityWithNulls);

        // Assert
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
        // Arrange - Simulando dados vindos do ViaCEP
        AddressDTO viaCepDto = new AddressDTO();
        viaCepDto.setLogradouro("Avenida Paulista");
        viaCepDto.setCidade("São Paulo");
        viaCepDto.setEstado("SP");
        viaCepDto.setCep("01310-100");
        // ID é null pois vem do ViaCEP

        // Act
        AddressEntity result = AddressMapper.toEntity(viaCepDto);

        // Assert
        assertNotNull(result);
        assertNull(result.getId()); // ID deve ser null pois será gerado pelo banco
        assertEquals("Avenida Paulista", result.getLogradouro());
        assertEquals("São Paulo", result.getCidade());
        assertEquals("SP", result.getEstado());
        assertEquals("01310-100", result.getCep());
    }

    @Test
    @DisplayName("Deve manter independência entre objetos após mapeamento")
    void testToEntity_ObjectIndependence() {
        // Act
        AddressEntity result = AddressMapper.toEntity(addressDTO);

        // Modificar o DTO original
        addressDTO.setLogradouro("Rua Augusta");
        addressDTO.setCidade("Rio de Janeiro");

        // Assert
        assertNotEquals(addressDTO.getLogradouro(), result.getLogradouro());
        assertNotEquals(addressDTO.getCidade(), result.getCidade());
        assertEquals("Avenida Paulista", result.getLogradouro()); // Valor original
        assertEquals("São Paulo", result.getCidade()); // Valor original
    }

    @Test
    @DisplayName("Deve manter independência entre objetos após mapeamento toDto")
    void testToDto_ObjectIndependence() {
        // Act
        AddressDTO result = AddressMapper.toDto(addressEntity);

        // Modificar a Entity original
        addressEntity.setLogradouro("Rua Augusta");
        addressEntity.setCidade("Rio de Janeiro");

        // Assert
        assertNotEquals(addressEntity.getLogradouro(), result.getLogradouro());
        assertNotEquals(addressEntity.getCidade(), result.getCidade());
        assertEquals("Avenida Paulista", result.getLogradouro()); // Valor original
        assertEquals("São Paulo", result.getCidade()); // Valor original
    }

    @Test
    @DisplayName("Deve converter diferentes tipos de logradouro")
    void testToEntity_DifferentLogradouroTypes() {
        // Arrange
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

            // Act
            AddressEntity result = AddressMapper.toEntity(addressDTO);

            // Assert
            assertEquals(logradouro, result.getLogradouro());
        }
    }

    @Test
    @DisplayName("Deve converter diferentes formatos de CEP")
    void testToEntity_DifferentCepFormats() {
        // Arrange
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

            // Act
            AddressEntity result = AddressMapper.toEntity(addressDTO);

            // Assert
            assertEquals(cep, result.getCep());
        }
    }

    @Test
    @DisplayName("Deve ignorar relacionamento Stadium no mapeamento")
    void testToEntity_IgnoresStadiumRelationship() {
        // Arrange
        StadiumEntity stadium = new StadiumEntity();
        stadium.setId(1L);
        stadium.setName("Arena Test");

        addressEntity.setStadium(stadium);

        // Act
        AddressDTO result = AddressMapper.toDto(addressEntity);

        // Assert
        assertNotNull(result);
        assertEquals(addressEntity.getId(), result.getId());
        assertEquals(addressEntity.getLogradouro(), result.getLogradouro());
        // Stadium não deve aparecer no DTO pois não há campo para isso
    }

    @Test
    @DisplayName("Deve fazer mapeamento bidirecional correto")
    void testBidirectionalMapping() {
        // Act - DTO -> Entity -> DTO
        AddressEntity entity = AddressMapper.toEntity(addressDTO);
        AddressDTO resultDto = AddressMapper.toDto(entity);

        // Assert
        assertEquals(addressDTO.getId(), resultDto.getId());
        assertEquals(addressDTO.getLogradouro(), resultDto.getLogradouro());
        assertEquals(addressDTO.getCidade(), resultDto.getCidade());
        assertEquals(addressDTO.getEstado(), resultDto.getEstado());
        assertEquals(addressDTO.getCep(), resultDto.getCep());

        // Act - Entity -> DTO -> Entity
        AddressDTO dto = AddressMapper.toDto(addressEntity);
        AddressEntity resultEntity = AddressMapper.toEntity(dto);

        // Assert
        assertEquals(addressEntity.getId(), resultEntity.getId());
        assertEquals(addressEntity.getLogradouro(), resultEntity.getLogradouro());
        assertEquals(addressEntity.getCidade(), resultEntity.getCidade());
        assertEquals(addressEntity.getEstado(), resultEntity.getEstado());
        assertEquals(addressEntity.getCep(), resultEntity.getCep());
    }

    @Test
    @DisplayName("Deve lidar com IDs de diferentes tipos")
    void testDifferentIdTypes() {
        // Arrange & Act & Assert
        Long[] ids = {1L, 0L, 999L, Long.MAX_VALUE, Long.MIN_VALUE};

        for (Long id : ids) {
            addressDTO.setId(id);
            AddressEntity result = AddressMapper.toEntity(addressDTO);
            assertEquals(id, result.getId());
        }
    }

    @Test
    @DisplayName("Deve preservar espaços em branco nos campos")
    void testWhitespacePreservation() {
        // Arrange
        addressDTO.setLogradouro(" Avenida Paulista ");
        addressDTO.setCidade("  São Paulo  ");
        addressDTO.setEstado(" SP ");
        addressDTO.setCep(" 01310-100 ");

        // Act
        AddressEntity result = AddressMapper.toEntity(addressDTO);

        // Assert
        assertEquals(" Avenida Paulista ", result.getLogradouro());
        assertEquals("  São Paulo  ", result.getCidade());
        assertEquals(" SP ", result.getEstado());
        assertEquals(" 01310-100 ", result.getCep());
    }

    @Test
    @DisplayName("Deve lidar com strings vazias")
    void testEmptyStrings() {
        // Arrange
        addressDTO.setLogradouro("");
        addressDTO.setCidade("");
        addressDTO.setEstado("");
        addressDTO.setCep("");

        // Act
        AddressEntity result = AddressMapper.toEntity(addressDTO);

        // Assert
        assertEquals("", result.getLogradouro());
        assertEquals("", result.getCidade());
        assertEquals("", result.getEstado());
        assertEquals("", result.getCep());
    }

    @Test
    @DisplayName("Deve funcionar com dados reais de endereços brasileiros")
    void testRealBrazilianAddresses() {
        // Arrange - Endereços reais para teste
        Object[][] realAddresses = {
                {1L, "Avenida Paulista", "São Paulo", "SP", "01310-100"},
                {2L, "Rua Oscar Freire", "São Paulo", "SP", "01426-001"},
                {3L, "Avenida Atlântica", "Rio de Janeiro", "RJ", "22070-000"},
                {4L, "Rua da Assembleia", "Rio de Janeiro", "RJ", "20011-000"},
                {5L, "Avenida Boa Viagem", "Recife", "PE", "51030-000"}
        };

        for (Object[] addressData : realAddresses) {
            // Arrange
            AddressDTO dto = new AddressDTO();
            dto.setId((Long) addressData[0]);
            dto.setLogradouro((String) addressData[1]);
            dto.setCidade((String) addressData[2]);
            dto.setEstado((String) addressData[3]);
            dto.setCep((String) addressData[4]);

            // Act
            AddressEntity entity = AddressMapper.toEntity(dto);
            AddressDTO resultDto = AddressMapper.toDto(entity);

            // Assert
            assertEquals(dto.getId(), resultDto.getId());
            assertEquals(dto.getLogradouro(), resultDto.getLogradouro());
            assertEquals(dto.getCidade(), resultDto.getCidade());
            assertEquals(dto.getEstado(), resultDto.getEstado());
            assertEquals(dto.getCep(), resultDto.getCep());
        }
    }
}
