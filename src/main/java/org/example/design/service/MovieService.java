package org.example.design.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.example.design.exception.NotFoundException;
import org.example.design.model.Movie;
import org.springframework.stereotype.Service;

import lombok.NonNull;


@Service
public class MovieService {

    private final Map<String, Movie> movies = new HashMap<>();
    
    
    public Movie getMovie(@NonNull final String movieId) {
        if (!movies.containsKey(movieId)) {
            throw new NotFoundException();
        }
        return movies.get(movieId);
    }

    public Movie createMovie(@NonNull final String movieName) {
        String movieId = UUID.randomUUID().toString();
        Movie movie = new Movie(movieId, movieName);
        movies.put(movieId, movie);
        return movie;
    }

}
