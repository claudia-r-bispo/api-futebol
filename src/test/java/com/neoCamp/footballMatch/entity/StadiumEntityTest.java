package com.neoCamp.footballMatch.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;

class StadiumEntityTest {

    @Test
    void createStadiumEntity_Success() {

        StadiumEntity stadium = new StadiumEntity();
        stadium.setId(1L);
        stadium.setName("Arena Test");
        stadium.setUf("SP");
        stadium.setActive(true);
        stadium.setDateCreation(LocalDate.of(2024, 1, 1));


        assertEquals(1L, stadium.getId());
        assertEquals("Arena Test", stadium.getName());
        assertEquals("SP", stadium.getUf());
        assertTrue(stadium.isActive());
        assertEquals(LocalDate.of(2024, 1, 1), stadium.getDateCreation());
    }

    @Test
    void createStadiumEntity_WithAddress() {

        AddressEntity address = new AddressEntity();
        address.setId(1L);
        address.setStreet("Avenida Paulista");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipCode("01310-100");


        StadiumEntity stadium = new StadiumEntity();
        stadium.setId(1L);
        stadium.setName("Arena Test");
        stadium.setUf("SP");
        stadium.setActive(true);
        stadium.setDateCreation(LocalDate.of(2024, 1, 1));
        stadium.setAddress(address);


        assertEquals(1L, stadium.getId());
        assertEquals("Arena Test", stadium.getName());
        assertEquals("SP", stadium.getUf());
        assertTrue(stadium.isActive());
        assertEquals(LocalDate.of(2024, 1, 1), stadium.getDateCreation());
        assertNotNull(stadium.getAddress());
        assertEquals("Avenida Paulista", stadium.getAddress().getStreet());
        assertEquals("São Paulo", stadium.getAddress().getCity());
    }

    @Test
    void createStadiumEntity_WithAllArgsConstructor() {

        AddressEntity address = new AddressEntity();
        address.setStreet("Avenida Paulista");
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipCode("01310-100");


        StadiumEntity stadium = new StadiumEntity(
                1L,                          // id
                "Arena Test",               // name
                "SP",                       // uf
                true,                        // active
                LocalDate.of(2024, 1, 1),    // dateCreation
                address,                     // address
                Collections.emptyList()      // addresses
        );


        assertEquals(1L, stadium.getId());
        assertEquals("Arena Test", stadium.getName());
        assertEquals("SP", stadium.getUf());
        assertTrue(stadium.isActive());
        assertEquals(LocalDate.of(2024, 1, 1), stadium.getDateCreation());
        assertNotNull(stadium.getAddress());
        assertEquals("Avenida Paulista", stadium.getAddress().getStreet());
        assertEquals("São Paulo", stadium.getAddress().getCity());
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


        stadium.setId(2L);
        stadium.setName("Estádio do Morumbi");
        stadium.setUf("SP");
        stadium.setActive(false);
        stadium.setDateCreation(testDate);


        assertEquals(2L, stadium.getId());
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
    void stadiumEntity_EqualsAndHashCode() {

        AddressEntity address1 = new AddressEntity();
        address1.setId(1L);

        AddressEntity address2 = new AddressEntity();
        address2.setId(1L);

        StadiumEntity stadium1 = new StadiumEntity();
        stadium1.setId(1L);
        stadium1.setName("Arena Test");
        stadium1.setAddress(address1);

        StadiumEntity stadium2 = new StadiumEntity();
        stadium2.setId(1L);
        stadium2.setName("Arena Test");
        stadium2.setAddress(address2);


        assertEquals(stadium1, stadium2);
        assertEquals(stadium1.hashCode(), stadium2.hashCode());
    }

    @Test
    void stadiumEntity_ToString() {

        StadiumEntity stadium = new StadiumEntity();
        stadium.setId(1L);
        stadium.setName("Arena Test");
        stadium.setUf("SP");


        String toString = stadium.toString();


        assertNotNull(toString);
        assertTrue(toString.contains("Arena Test"));
        assertTrue(toString.contains("SP"));
    }
}