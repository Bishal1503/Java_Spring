package com.JPoP2.LibraryService.controller;

import com.JPoP2.LibraryService.model.Library;
import com.JPoP2.LibraryService.repository.LibraryRepository;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LibraryRestControllerTest {
    private static final ObjectMapper om = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryRepository mockLibraryRepository;

    @Before
    public void init() {
        Library library = new Library(1L,"A@2", "The Forest of Enchantments", "Chitra Banerjee Divakaruni", "BPB", 2, 2, new BigDecimal("353.50"));
        when(mockLibraryRepository.findById(1l)).thenReturn(Optional.of(library));
    }

    @Test
    public void find_Id_OK() throws Exception {
        mockMvc.perform(get("/lib/books/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1L)))
                .andExpect(jsonPath("$.callno", is("A@2")))
                .andExpect(jsonPath("$.name", is("The Forest of Enchantments")))
                .andExpect(jsonPath("$.author", is("Chitra Banerjee Divakaruni")))
                .andExpect(jsonPath("$.publisher", is("BPB")))
                .andExpect(jsonPath("$.quantity", is(2)))
                .andExpect(jsonPath("$.issued", is(2)))
                .andExpect(jsonPath("$.price", is(353.50)));
        verify(mockLibraryRepository, times(1)).findById(1L);
    }


    @Test
    public void find_All_OK() throws Exception {
        List<Library> libs = Arrays.asList(
                new Library(2L, "B@1", "The Forest of Enchantments", "Chitra Banerjee Divakaruni", "Pearson", 3, 0, new BigDecimal("353.50")),
                new Library(3L, "G@12", "The Carve the Mark", "Veronica Roth", "BPB", 10, 0, new BigDecimal("742.50")));

        when(mockLibraryRepository.findAll()).thenReturn(libs);
        mockMvc.perform(get("/lib/books"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.id", is(2L)))
                .andExpect(jsonPath("$.callno", is("B@1")))
                .andExpect(jsonPath("$.name", is("The Forest of Enchantments")))
                .andExpect(jsonPath("$.author", is("Chitra Banerjee Divakaruni")))
                .andExpect(jsonPath("$.publisher", is("Pearson")))
                .andExpect(jsonPath("$.quantity", is(3)))
                .andExpect(jsonPath("$.issued", is(0)))
                .andExpect(jsonPath("$.price", is(353.50)))
                .andExpect(jsonPath("$.id", is(3L)))
                .andExpect(jsonPath("$.callno", is("G@12")))
                .andExpect(jsonPath("$.name", is("The Carve the Mark")))
                .andExpect(jsonPath("$.author", is("Veronica Roth")))
                .andExpect(jsonPath("$.publisher", is("BPB")))
                .andExpect(jsonPath("$.quantity", is(10)))
                .andExpect(jsonPath("$.issued", is(0)))
                .andExpect(jsonPath("$.price", is(742.50)));
        verify(mockLibraryRepository, times(1)).findAll();
    }


    @Test
    public void find_IdNotFound_404() throws Exception {
        mockMvc.perform(get("/lib/books/5")).andExpect(status().isNotFound());
    }


    @Test
    public void save_OK() throws Exception {
        Library newLibrary = new Library(2L, "B@1", "The Forest of Enchantments", "Chitra Banerjee Divakaruni", "Pearson", 3, 0, new BigDecimal("353.50"));
        when(mockLibraryRepository.save(any(Library.class))).thenReturn(newLibrary);
        mockMvc.perform(post("/lib/books")
                .content(om.writeValueAsString(newLibrary))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2L)))
                .andExpect(jsonPath("$.callno", is("B@1")))
                .andExpect(jsonPath("$.name", is("The Forest of Enchantments")))
                .andExpect(jsonPath("$.author", is("Chitra Banerjee Divakaruni")))
                .andExpect(jsonPath("$.publisher", is("Pearson")))
                .andExpect(jsonPath("$.quantity", is(3)))
                .andExpect(jsonPath("$.issued", is(0)))
                .andExpect(jsonPath("$.price", is(353.50)));
        verify(mockLibraryRepository, times(1)).save(any(Library.class));
    }


    @Test
    public void update_OK() throws Exception {
        Library updateLibrary = new Library(2L, "B@1", "The Forest of Enchantments", "Chitra Banerjee Divakaruni", "Pearson", 3, 0, new BigDecimal("353.50"));
        when(mockLibraryRepository.save(any(Library.class))).thenReturn(updateLibrary);
        mockMvc.perform(put("/lib/books/2")
                .content(om.writeValueAsString(updateLibrary))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(2L)))
                .andExpect(jsonPath("$.callno", is("B@1")))
                .andExpect(jsonPath("$.name", is("The Forest of Enchantments")))
                .andExpect(jsonPath("$.author", is("Chitra Banerjee Divakaruni")))
                .andExpect(jsonPath("$.publisher", is("Pearson")))
                .andExpect(jsonPath("$.quantity", is(3)))
                .andExpect(jsonPath("$.issued", is(0)))
                .andExpect(jsonPath("$.price", is(353.50)));
    }


    @Test
    public void patch_Author_OK() throws Exception {
        when(mockLibraryRepository.save(any(Library.class))).thenReturn(new Library());
        String patchInJson = "{\"author\":\"ultraman\"}";
        mockMvc.perform(patch("/lib/books/1")
                .content(patchInJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
        verify(mockLibraryRepository, times(1)).findById(1L);
        verify(mockLibraryRepository, times(1)).save(any(Library.class));
    }


    @Test
    public void patch_Price_405() throws Exception {
        String patchInJson = "{\"price\":\"99.99\"}";
        mockMvc.perform(patch("/lib/books/1")
                .content(patchInJson)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
        verify(mockLibraryRepository, times(1)).findById(1L);
        verify(mockLibraryRepository, times(0)).save(any(Library.class));
    }


    @Test
    public void delete_OK() throws Exception {
        doNothing().when(mockLibraryRepository).deleteById(1L);
        mockMvc.perform(delete("/lib/books/1"))
                .andExpect(status().isOk());
        verify(mockLibraryRepository, times(1)).deleteById(1L);
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
