package com.example.demo.services;

import com.example.demo.exceptions.RequiredObjectIsNullException;
import com.example.demo.model.Book;
import com.example.demo.repositories.BookRepository;
import com.example.demo.unittests.mapper.mocks.MockBook;
import com.example.demo.valueobject.BookVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    MockBook input;

    @InjectMocks
    private BookService service;

    @Mock
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        input = new MockBook();
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void findAll() {
        List<Book> list = input.mockEntityList();

        Mockito.when(repository.findAll()).thenReturn(list);

        var people = service.findAll();

        assertNotNull(people);
        assertEquals(14, people.size());

        var bookOne = people.get(1);

        assertNotNull(bookOne);
        assertNotNull(bookOne.getKey());
        assertNotNull(bookOne.getLinks());
        assertTrue(bookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", bookOne.getAuthor());
        assertNotNull(bookOne.getLaunchDate());
        assertEquals(1.0, bookOne.getPrice());
        assertEquals("Title Test1", bookOne.getTitle());

        var bookFour = people.get(4);

        assertNotNull(bookFour);
        assertNotNull(bookFour.getKey());
        assertNotNull(bookFour.getLinks());
        assertTrue(bookFour.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));
        assertEquals("Author Test4", bookFour.getAuthor());
        assertNotNull(bookFour.getLaunchDate());
        assertEquals(4.0, bookFour.getPrice());
        assertEquals("Title Test4", bookFour.getTitle());

        var bookSeven = people.get(7);

        assertNotNull(bookSeven);
        assertNotNull(bookSeven.getKey());
        assertNotNull(bookSeven.getLinks());
        assertTrue(bookSeven.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));
        assertEquals("Author Test7", bookSeven.getAuthor());
        assertNotNull(bookSeven.getLaunchDate());
        assertEquals(7.0, bookSeven.getPrice());
        assertEquals("Title Test7", bookSeven.getTitle());
    }*/

    @Test
    void findById() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(entity));

        var result = service.findById(1L);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(1.0, result.getPrice());
        assertEquals("Title Test1", result.getTitle());
    }

    @Test
    void create() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        Mockito.when(repository.save(Mockito.any())).thenReturn(entity);

        var result = service.create(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(1.0, result.getPrice());
        assertEquals("Title Test1", result.getTitle());
    }

    @Test
    void createWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.create(null);
        });
        String expectedMessage = "It's not allowed to persist a null object";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void update() {
        Book entity = input.mockEntity(1);
        entity.setId(1L);

        BookVO vo = input.mockVO(1);
        vo.setKey(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(entity));
        Mockito.when(repository.save(Mockito.any())).thenReturn(entity);

        var result = service.update(vo);
        assertNotNull(result);
        assertNotNull(result.getKey());
        assertNotNull(result.getLinks());
        assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));
        assertEquals("Author Test1", result.getAuthor());
        assertNotNull(result.getLaunchDate());
        assertEquals(1.0, result.getPrice());
        assertEquals("Title Test1", result.getTitle());
    }

    @Test
    void updateWithNullBook() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
            service.update(null);
        });
        String expectedMessage = "It's not allowed to persist a null object";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void delete() {
        Book entity = input.mockEntity();
        entity.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(entity));

        service.delete(1L);
    }
}