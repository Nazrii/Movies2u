package com.movies2u.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class allows the CRUD function on the movies through various rest api methods
 */
@RestController
@RequestMapping(path = "api/movie")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Retrieve all available movies
     *
     * Sample URL = http://localhost:8080/api/movie
     * @return List<Movie>
     */
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    /**
     * Retrieve a movie via a movieId
     *
     * Sample URL = http://localhost:8080/api/movie/1
     * @param movieId movie id
     * @return Movie
     */
    @GetMapping(path = "{movieId}")
    public Movie getMovieById(@PathVariable("movieId") Long movieId) {
        return movieService.getMovieById(movieId);
    }

    /**
     * Retrieve a list of movies via a list of movie ids
     *
     * Sample URL = http://localhost:8080/api/movie/movies?movieIds=1,2
     * @param movieIds list of movie ids
     * @return list of movies
     */
    @GetMapping(path = "movies")
    public List<Movie> getMoviesByIds(@RequestParam List<Long> movieIds) {
        return movieService.getMoviesByIds(movieIds);
    }

    /**
     * Create a new movie via json which contains movie properties
     *
     * Sample URL =  http://localhost:8080/api/movie
     * Sample JSON = {
     *     "title":"Ombak Rindu",
     *     "category": "Romance",
     *     "starRating": 2.0
     *     }
     * @param movie movie
     */
    @PostMapping
    public ResponseEntity createNewMovie(@RequestBody Movie movie) {
        movieService.addNewMovie(movie);
        return new ResponseEntity("Movie has been successfully created", true);
    }

    /**
     * Delete a movie via a movie id
     *
     * Sample URL = http://localhost:8080/api/movie/delete/3
     * @param movieId movie id
     */
    @DeleteMapping(path = "delete/{movieId}")
    public ResponseEntity deleteMovie(@PathVariable("movieId") Long movieId) {
        movieService.deleteMovie(movieId);
        return new ResponseEntity("Movie has been successfully deleted", true);
    }

    /**
     * Update a movie via json which contains any of the movie properties
     *
     * Sample URL = localhost:8080/api/movie/edit/3
     * Sample JSON =   {
     *     "title":"Project Power",
     *     "starRating":4
     *     }
     * @param movieId movie id
     * @param movie movie
     */
    @PutMapping(path = "edit/{movieId}")
    public ResponseEntity updateMovie(@PathVariable("movieId") Long movieId,
                            @RequestBody(required = false) Movie movie) {
        movieService.updateMovie(movieId, movie);
        return new ResponseEntity("Movie has been successfully updated", true);
    }
}
