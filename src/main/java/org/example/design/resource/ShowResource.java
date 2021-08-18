package org.example.design.resource;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.example.design.model.Movie;
import org.example.design.model.Screen;
import org.example.design.model.Seat;
import org.example.design.model.Show;
import org.example.design.service.MovieService;
import org.example.design.service.SeatAvailabilityService;
import org.example.design.service.ShowService;
import org.example.design.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.NonNull;


@Path("/v1/show")
@Produces(MediaType.APPLICATION_JSON)
public class ShowResource {
	
	@Autowired
	private SeatAvailabilityService seatAvailabilityService;
	
	@Autowired
	private ShowService showService;
    
	@Autowired
	private TheatreService theatreService;
    
	@Autowired
	private MovieService movieService;
	
	
	
	
	
    public String createShow(@NonNull final String movieId, @NonNull final String screenId, @NonNull final Date startTime, @NonNull final Integer durationInSeconds) {
        Screen screen = theatreService.getScreen(screenId);
        Movie movie = movieService.getMovie(movieId);
        return showService.createShow(movie, screen, startTime, durationInSeconds).getId();
    }
    
    public List<String> getAvailableSeats(@NonNull final String showId) {
        Show show = showService.getShow(showId);
        List<Seat> availableSeats = seatAvailabilityService.getAvailableSeats(show);
        return availableSeats.stream().map(seat -> seat.getId()).collect(Collectors.toList());
    }
    
}
