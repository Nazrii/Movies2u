package com.movies2u.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTests {

    private static final String CREATE_SUCCESS = "Movie has been successfully created";
    private static final String DELETE_SUCCESS = "Movie has been successfully deleted";
    private static final String UPDATE_SUCCESS = "Movie has been successfully updated";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieService movieService;

    @Test
    void testGetAllMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Extraction", "Action", 3.5));
        movies.add(new Movie("Over the Moon", "Musical", 4.5));
        movies.add(new Movie("Time to Hunt", "Thriller", 3.0));
        Mockito.when(movieService.getAllMovies()).thenReturn((movies));

        MvcResult mvcResult = mockMvc.perform(get("/api/movie")).andExpect(status().isOk()).andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        System.out.println("JsonResponse = " + jsonResponse);
        String expectedJsonResponse = objectMapper.writeValueAsString(movies);

        Assertions.assertEquals(jsonResponse, expectedJsonResponse);
    }

    @Test
    void testGetMovieById() throws Exception {
        Long movieId = 5L;
        Movie movie = new Movie("Extraction", "Action", 3.5);
        Mockito.when(movieService.getMovieById(movieId)).thenReturn(movie);

        MvcResult mvcResult = mockMvc.perform(get("/api/movie/" + movieId)).andExpect(status().isOk()).andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        System.out.println("JsonResponse = " + jsonResponse);
        String expectedJsonResponse = objectMapper.writeValueAsString(movie);

        Assertions.assertEquals(jsonResponse, expectedJsonResponse);
    }

    @Test
    void testGetMoviesByIds() throws Exception {
        Long first = 5L;
        Long second = 6L;

        List<Long> movieIds = new ArrayList<>();
        movieIds.add(first);
        movieIds.add(second);
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Extraction", "Action", 3.5));
        movies.add(new Movie("Over the Moon", "Musical", 4.5));
        Mockito.when(movieService.getMoviesByIds(movieIds)).thenReturn(movies);

        MvcResult mvcResult = mockMvc.perform(get("/api/movie/movies")
                .param("movieIds", first + "," + second))
                .andExpect(status().isOk())
                .andReturn();
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        System.out.println("JsonResponse = " + jsonResponse);
        String expectedJsonResponse = objectMapper.writeValueAsString(movies);

        Assertions.assertEquals(jsonResponse, expectedJsonResponse);
    }

    @Test
    void testCreateNewMovie() throws Exception {
        Movie movie = new Movie("Extraction", "Action", 3.5);
        MvcResult mvcResult = mockMvc.perform(post("/api/movie")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        System.out.println("response = " + response);
        ResponseEntity responseEntity = objectMapper.readValue(response, ResponseEntity.class);

        Assertions.assertEquals(Boolean.TRUE, responseEntity.isSuccess());
        Assertions.assertEquals(CREATE_SUCCESS, responseEntity.getMessage());
    }

    @Test
    void testDeleteNewMovie() throws Exception {
        Long movieId = 5L;
        MvcResult mvcResult = mockMvc.perform(delete("/api/movie/delete/" + movieId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(movieId)))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        System.out.println("response = " + response);
        ResponseEntity responseEntity = objectMapper.readValue(response, ResponseEntity.class);

        Assertions.assertEquals(Boolean.TRUE, responseEntity.isSuccess());
        Assertions.assertEquals(DELETE_SUCCESS, responseEntity.getMessage());
    }

    @Test
    void testUpdateNewMovie() throws Exception {
        String movieId = "5";
        Movie movie = new Movie("Extraction", "Action", 3.5);
        MvcResult mvcResult = mockMvc.perform(put("/api/movie/edit/" + movieId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk())
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        System.out.println("response = " + response);
        ResponseEntity responseEntity = objectMapper.readValue(response, ResponseEntity.class);

        Assertions.assertEquals(Boolean.TRUE, responseEntity.isSuccess());
        Assertions.assertEquals(UPDATE_SUCCESS, responseEntity.getMessage());
    }
}
