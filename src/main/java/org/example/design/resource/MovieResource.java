package org.example.design.resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.example.design.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.NonNull;


@Path("/v1/movies")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {

	@Autowired
	private MovieService movieService;

	public String createMovie(@NonNull final String movieName) {
		return movieService.createMovie(movieName).getId();
	}

}
