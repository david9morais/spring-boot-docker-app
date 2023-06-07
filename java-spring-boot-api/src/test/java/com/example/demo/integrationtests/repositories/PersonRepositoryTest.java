package com.example.demo.integrationtests.repositories;


import com.example.demo.integrationtests.testcontainers.AbstractIntegrationTest;
import com.example.demo.model.Person;
import com.example.demo.repositories.PersonRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PersonRepository repository;

    private static Person person;

    @BeforeAll
    public static void setUp() {
        person = new Person();
    }

    @Test
    @Order(0)
    public void testFindPeopleByName() {
        Pageable pageable = PageRequest.of(2, 10, Sort.by(Sort.Direction.ASC, "firstName"));
        person = repository.findPeopleByName("be", pageable).getContent().get(1);

        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());

        assertTrue(person.getEnabled());

        assertEquals(809,person.getId());
        assertEquals("Berthe", person.getFirstName());
        assertEquals("Mont", person.getLastName());
        assertEquals("792 Eggendart Street", person.getAddress());
        assertEquals("Female", person.getGender());

    }

    @Test
    @Order(1)
    public void testDisablePerson() {
        repository.disablePerson(person.getId());

        Pageable pageable = PageRequest.of(2, 10, Sort.by(Sort.Direction.ASC, "firstName"));
        person = repository.findPeopleByName("be", pageable).getContent().get(1);

        assertNotNull(person);
        assertNotNull(person.getId());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getAddress());
        assertNotNull(person.getGender());

        assertTrue(!person.getEnabled());

        assertEquals(809,person.getId());
        assertEquals("Berthe", person.getFirstName());
        assertEquals("Mont", person.getLastName());
        assertEquals("792 Eggendart Street", person.getAddress());
        assertEquals("Female", person.getGender());

    }
}
