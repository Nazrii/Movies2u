package com.movies2u.movie;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@WebMvcTest(MovieService.class)
class MovieServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieRepository movieRepository;

    private MovieService movieService;

    @Test
    void testGetAllMovies() {
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Extraction", "Action", 3.5));
        movies.add(new Movie("Over the Moon", "Musical", 4.5));
        movies.add(new Movie("Time to Hunt", "Thriller", 3.0));
        Mockito.when(movieRepository.findAll()).thenReturn((movies));

        movieService = new MovieService(movieRepository);
        List<Movie> dbMovies = movieService.getAllMovies();
        System.out.println("dbMovies " + dbMovies);

        Assertions.assertEquals(movies, dbMovies);
    }

    @Test
    void testGetMovieById() {
        Long movieId = 5L;
        Movie movie = new Movie("Extraction", "Action", 3.5);
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        movieService = new MovieService(movieRepository);
        Movie dbMovie = movieService.getMovieById(movieId);
        System.out.println("dbMovie.getTitle " + dbMovie.getTitle());

        Assertions.assertEquals(movie, dbMovie);
    }

    @Test
    void testGetMovieByIdNotExist() {
        Long movieId = 5L;
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        movieService = new MovieService(movieRepository);
        assertThatThrownBy(() -> movieService.getMovieById(movieId)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testGetMoviesByIds() {
        Long first = 5L;
        Long second = 6L;

        List<Long> movieIds = new ArrayList<>();
        movieIds.add(first);
        movieIds.add(second);

        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Extraction", "Action", 3.5));
        movies.add(new Movie("Over the Moon", "Musical", 4.5));
        movies.add(new Movie("Time to Hunt", "Thriller", 3.0));
        Mockito.when(movieRepository.findByMovieIdIn(movieIds)).thenReturn(movies);

        movieService = new MovieService(movieRepository);
        List<Movie> dbMovies = movieService.getMoviesByIds(movieIds);
        System.out.println("dbMovies " + dbMovies);

        Assertions.assertEquals(movies, dbMovies);
    }

    @Test
    void testAddNewMovie() {
        String title = "Extraction";
        Movie movie = new Movie(title, "Action", 3.5);
        Mockito.when(movieRepository.findMovieByTitle(title)).thenReturn(Optional.empty());

        movieService = new MovieService(movieRepository);
        assertThatNoException().isThrownBy(() -> movieService.addNewMovie(movie));
    }

    @Test
    void testAddNewMovieAlreadyExists() {
        String title = "Extraction";
        Movie movie = new Movie(title, "Action", 3.5);
        Mockito.when(movieRepository.findMovieByTitle(title)).thenReturn(Optional.of(movie));

        movieService = new MovieService(movieRepository);
        assertThatThrownBy(() -> movieService.addNewMovie(movie)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testDeleteMovie() {
        Long movieId = 5L;
        Mockito.when(movieRepository.existsById(movieId)).thenReturn(Boolean.TRUE);

        movieService = new MovieService(movieRepository);
        assertThatNoException().isThrownBy(() -> movieService.deleteMovie(movieId));
    }

    @Test
    void testDeleteMovieNotExists() {
        Long movieId = 5L;
        Mockito.when(movieRepository.existsById(movieId)).thenReturn(Boolean.FALSE);

        movieService = new MovieService(movieRepository);
        assertThatThrownBy(() -> movieService.deleteMovie(movieId)).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testUpdateMovie() {
        Long movieId = 5L;
        Movie movie = new Movie("Extraction", "Action", 3.5);
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        movieService = new MovieService(movieRepository);
        assertThatNoException().isThrownBy(() -> movieService.updateMovie(movieId, movie));
    }

    @Test
    void testUpdateMovieNotExists() {
        Long movieId = 5L;
        Movie movie = new Movie("Extraction", "Action", 3.5);
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.empty());

        movieService = new MovieService(movieRepository);
        assertThatThrownBy(() -> movieService.updateMovie(movieId, movie)).isInstanceOf(IllegalStateException.class);
    }
}
