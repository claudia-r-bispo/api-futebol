package com.neoCamp.footballMatch.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;
import java.util.UUID;

class StadiumEntityTest {

    @Test
    void createStadiumEntity_Success() {
        // Arrange
        UUID stadiumId = UUID.randomUUID();
        StadiumEntity stadium = new StadiumEntity();
        stadium.setId(stadiumId);
        stadium.setName("Arena Test");
        stadium.setUf("SP");
        stadium.setActive(true);
        stadium.setDateCreation(LocalDate.of(2024, 1, 1));

        // Assert
        assertEquals(stadiumId, stadium.getId());
        assertEquals("Arena Test", stadium.getName());
        assertEquals("SP", stadium.getUf());
        assertTrue(stadium.isActive());
        assertEquals(LocalDate.of(2024, 1, 1), stadium.getDateCreation());
    }

    @Test
    void createStadiumEntity_WithAddress() {
        // Arrange
        UUID addressId = UUID.randomUUID();
        UUID stadiumId = UUID.randomUUID();
        
        AddressEntity address = new AddressEntity();
        address.setId(addressId);
        address.setStreet("Avenida Paulista");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipCode("01310-100");

        StadiumEntity stadium = new StadiumEntity();
        stadium.setId(stadiumId);
        stadium.setName("Arena Test");
        stadium.setUf("SP");
        stadium.setActive(true);
        stadium.setDateCreation(LocalDate.of(2024, 1, 1));
        stadium.setAddress(address);

        // Assert
        assertEquals(stadiumId, stadium.getId());
        assertEquals("Arena Test", stadium.getName());
        assertEquals("SP", stadium.getUf());
        assertTrue(stadium.isActive());
        assertEquals(LocalDate.of(2024, 1, 1), stadium.getDateCreation());
        assertNotNull(stadium.getAddress());
        assertEquals(addressId, stadium.getAddress().getId());
        assertEquals("Avenida Paulista", stadium.getAddress().getStreet());
        assertEquals("São Paulo", stadium.getAddress().getCity());
        assertEquals("SP", stadium.getAddress().getState());
        assertEquals("01310-100", stadium.getAddress().getZipCode());
    }

    @Test
    void createStadiumEntity_WithAllArgsConstructor() {

        UUID addressId = UUID.randomUUID();
        UUID stadiumId = UUID.randomUUID();
        
        AddressEntity address = new AddressEntity();
        address.setId(addressId);
        address.setStreet("Avenida Paulista");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipCode("01310-100");

        StadiumEntity stadium = new StadiumEntity(
                stadiumId,                          // id
                "Arena Test",               // name
                "SP",                       // uf
                true,                        // active
                LocalDate.of(2024, 1, 1),    // dateCreation
                address                      // address
        );


        assertEquals(stadiumId, stadium.getId());
        assertEquals("Arena Test", stadium.getName());
        assertEquals("SP", stadium.getUf());
        assertTrue(stadium.isActive());
        assertEquals(LocalDate.of(2024, 1, 1), stadium.getDateCreation());
        assertNotNull(stadium.getAddress());
        assertEquals(addressId, stadium.getAddress().getId());
        assertEquals("Avenida Paulista", stadium.getAddress().getStreet());
        assertEquals("São Paulo", stadium.getAddress().getCity());
        assertEquals("SP", stadium.getAddress().getState());
        assertEquals("01310-100", stadium.getAddress().getZipCode());
    }

    @Test
    void stadiumEntity_DefaultActive() {

        StadiumEntity stadium = new StadiumEntity();
        stadium.setName("Arena Test");


        assertTrue(stadium.isActive());
    }

    @Test
    void stadiumEntity_SettersAndGetters() {

        StadiumEntity stadium = new StadiumEntity();
        LocalDate testDate = LocalDate.of(2023, 6, 15);
        UUID stadiumId = UUID.randomUUID();

        stadium.setId(stadiumId);
        stadium.setName("Estádio do Morumbi");
        stadium.setUf("SP");
        stadium.setActive(false);
        stadium.setDateCreation(testDate);


        assertEquals(stadiumId, stadium.getId());
        assertEquals("Estádio do Morumbi", stadium.getName());
        assertEquals("SP", stadium.getUf());
        assertFalse(stadium.isActive());
        assertEquals(testDate, stadium.getDateCreation());
    }

    @Test
    void stadiumEntity_NullAddress() {

        StadiumEntity stadium = new StadiumEntity();
        stadium.setName("Arena Test");
        stadium.setAddress(null);


        assertNull(stadium.getAddress());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        
        StadiumEntity stadium1 = new StadiumEntity();
        stadium1.setId(id1);
        
        StadiumEntity stadium2 = new StadiumEntity();
        stadium2.setId(id1);
        
        StadiumEntity stadium3 = new StadiumEntity();
        stadium3.setId(id2);
        
        // Assert
        assertEquals(stadium1, stadium2);
        assertEquals(stadium1.hashCode(), stadium2.hashCode());
        assertNotEquals(stadium1, stadium3);
        assertNotEquals(stadium1, null);
        assertNotEquals(stadium1, new Object());
    }

    @Test
    void stadiumEntity_ToString() {

        StadiumEntity stadium = new StadiumEntity();
        stadium.setId(UUID.randomUUID());
        stadium.setName("Arena Test");
        stadium.setUf("SP");


        String toString = stadium.toString();


        assertNotNull(toString);
        assertTrue(toString.contains("Arena Test"));
        assertTrue(toString.contains("SP"));
    }
}