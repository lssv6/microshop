package com.microshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.microshop.model.Manufacturer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

// Actually these tests are not needed.
// Although you can test JPA repositories, they already work out of the box;
@DataJpaTest(showSql = true)
class ManufacturerRepositoryTest {

    @Autowired private ManufacturerRepository manufacturerRepository;

    @Test
    void testFindById() {
        Manufacturer man = manufacturerRepository.findById(99L).get();
        assertEquals(99L, man.getId());
        assertEquals("https://www.example.com/mLogo.png", man.getImg());
        assertEquals("BIC", man.getName());
        assertEquals(2L, man.getVersion());
    }
}
