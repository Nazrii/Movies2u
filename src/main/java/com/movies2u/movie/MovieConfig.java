package com.movies2u.movie;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MovieConfig {

    @Bean
    CommandLineRunner commandLineRunner(MovieRepository movieRepository) {
        return args -> {
            Movie avengers = new Movie("Avengers Endgame", "Action", 5.0);
            Movie tenet = new Movie("Tenet", "Action", 5.0);

            movieRepository.saveAll(List.of(avengers, tenet));
        };
    }
}
