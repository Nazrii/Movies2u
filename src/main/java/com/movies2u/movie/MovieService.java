package com.movies2u.movie;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service class which returns data related function from repository
 */
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    /**
     * Get all movies from repository
     * @return List<Movie>
     */
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    /**
     * Get movie by id from repository
     * @param movieId movie id
     * @return Movie object movie
     */
    public Movie getMovieById(Long movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            return movie.get();
        } else {
            throw new IllegalStateException("Movie does not exist");
        }
    }

    /**
     * Get list of movies by a list of ids from repository
     * @param movieIds list of movie ids
     * @return list of movies
     */
    public List<Movie> getMoviesByIds(List<Long> movieIds) {
        return movieRepository.findByMovieIdIn(movieIds);
    }

    /**
     * Add new movie to repository
     * @param movie movie
     */
    public void addNewMovie(Movie movie) {
        Optional<Movie> movieOptional = movieRepository.findMovieByTitle(movie.getTitle());
        if (movieOptional.isPresent()) {
            throw new IllegalStateException("Movie already exist");
        }
        movieRepository.save(movie);
    }

    /**
     * Delete a movie from repository
     * @param movieId movie id
     */
    public void deleteMovie(Long movieId) {
        boolean exists = movieRepository.existsById(movieId);
        if (!exists) {
            throw new IllegalStateException("Movie Id " + movieId + " does not exists");
        }
        movieRepository.deleteById(movieId);
    }

    /**
     * Update a movie to the repository
     * @param movieId movie id
     * @param movie movie
     */
    public void updateMovie(Long movieId, Movie movie) {
        Movie dbMovie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalStateException(
                        "Movie with id " + movieId + " does not exist"
                ));

        if (!StringUtils.isEmpty(movie.getTitle()) &&
                !Objects.equals(dbMovie.getTitle(), movie.getTitle())) {
            dbMovie.setTitle(movie.getTitle());
        }

        if (!StringUtils.isEmpty(movie.getCategory()) &&
                !Objects.equals(dbMovie.getCategory(), movie.getCategory())) {
            dbMovie.setCategory(movie.getCategory());
        }

        if (movie.getStarRating() != null &&
                !Objects.equals(dbMovie.getStarRating(), movie.getStarRating())) {
            dbMovie.setStarRating(movie.getStarRating());
        }

        movieRepository.save(dbMovie);
    }
}
