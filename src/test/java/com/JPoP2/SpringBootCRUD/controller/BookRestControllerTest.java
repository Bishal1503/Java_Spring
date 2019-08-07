package com.JPoP2.SpringBootCRUD.controller;

import com.JPoP2.SpringBootCRUD.model.Book;
import com.JPoP2.SpringBootCRUD.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookRestControllerTest {
    private static final ObjectMapper om = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository mockBookRepository;

    @Before
    public void init() {
        Book book = new Book(1L, "The Forest of Enchantments", "Chitra Banerjee Divakaruni", new BigDecimal("353.50"));
        when(mockBookRepository.findById(1L)).thenReturn(Optional.of(book));
    }

    @Test
    public void find_Id_OK() throws Exception {
        mockMvc.perform(get("/books/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("The Forest of Enchantments")))
                .andExpect(jsonPath("$.author", is("Chitra Banerjee Divakaruni")))
                .andExpect(jsonPath("$.price", is(353.50)));
        verify(mockBookRepository, times(1)).findById(1L);
    }


    @Test
    public void find_All_OK() throws Exception {
        List<Book> books = Arrays.asList(
                new Book(1L, "The Forest of Enchantments", "Chitra Banerjee Divakaruni", new BigDecimal("353.50")),
                new Book(2L, "The Carve the Mark", "Veronica Roth", new BigDecimal("742.50")));

        when(mockBookRepository.findAll()).thenReturn(books);
        mockMvc.perform(get("/books"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("The Forest of Enchantments")))
                .andExpect(jsonPath("$[0].author", is("Chitra Banerjee Divakaruni")))
                .andExpect(jsonPath("$[0].price", is(353.50)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("The Carve the Mark")))
                .andExpect(jsonPath("$[1].author", is("Veronica Roth")))
                .andExpect(jsonPath("$[1].price", is(742.50)));
        verify(mockBookRepository, times(1)).findAll();
    }


    @Test
    public void find_IdNotFound_404() throws Exception {
        mockMvc.perform(get("/books/5")).andExpect(status().isNotFound());
    }


    @Test
    public void save_OK() throws Exception {
        Book newBook = new Book(1L, "The Forest of Enchantments", "Chitra Banerjee Divakaruni", new BigDecimal("353.50"));
        when(mockBookRepository.save(any(Book.class))).thenReturn(newBook);
        mockMvc.perform(post("/books")
                .content(om.writeValueAsString(newBook))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("The Forest of Enchantments")))
                .andExpect(jsonPath("$.author", is("Chitra Banerjee Divakaruni")))
                .andExpect(jsonPath("$.price", is(353.50)));
        verify(mockBookRepository, times(1)).save(any(Book.class));
    }


    @Test
    public void update_OK() throws Exception {
        Book updateBook = new Book(1L, "The Forest of Enchantments", "Chitra Banerjee Divakaruni", new BigDecimal("353.50"));
        when(mockBookRepository.save(any(Book.class))).thenReturn(updateBook);
        mockMvc.perform(put("/books/1")
                .content(om.writeValueAsString(updateBook))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("The Forest of Enchantments")))
                .andExpect(jsonPath("$.author", is("Chitra Banerjee Divakaruni")))
                .andExpect(jsonPath("$.price", is(353.50)));
    }


    @Test
    public void patch_Author_OK() throws Exception {
        when(mockBookRepository.save(any(Book.class))).thenReturn(new Book());
        String patchInJson = "{\"author\":\"ultraman\"}";
        mockMvc.perform(patch("/books/1")
                .content(patchInJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        verify(mockBookRepository, times(1)).findById(1L);
        verify(mockBookRepository, times(1)).save(any(Book.class));
    }


    @Test
    public void patch_Price_405() throws Exception {
        String patchInJson = "{\"price\":\"99.99\"}";
        mockMvc.perform(patch("/books/1")
                .content(patchInJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
        verify(mockBookRepository, times(1)).findById(1L);
        verify(mockBookRepository, times(0)).save(any(Book.class));
    }


    @Test
    public void delete_OK() throws Exception {
        doNothing().when(mockBookRepository).deleteById(1L);
        mockMvc.perform(delete("/books/1"))
                /*.andDo(print())*/
                .andExpect(status().isOk());
        verify(mockBookRepository, times(1)).deleteById(1L);
    }

    private static void printJSON(Object object) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
