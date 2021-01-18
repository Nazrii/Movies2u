package com.movies2u;

import com.movies2u.movie.Movie;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class Movies2uApplication {

	public static void main(String[] args) {
		SpringApplication.run(Movies2uApplication.class, args);
	}
}
