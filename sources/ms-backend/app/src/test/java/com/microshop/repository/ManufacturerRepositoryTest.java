package com.microshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.microshop.model.Manufacturer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

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

    @Test
    void shouldThrowErrorWithinDuplicates() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("BIC");
        manufacturer.setImg("https://www.example.com/mLogo.png");
        assertThrows(
                DataIntegrityViolationException.class,
                () -> {
                    manufacturerRepository.saveAndFlush(manufacturer);
                    manufacturerRepository.saveAndFlush(manufacturer);
                });
    }
}
