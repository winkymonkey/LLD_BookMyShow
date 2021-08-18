package org.example.design.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.example.design.model.Seat;
import org.example.design.model.Show;
import org.example.design.service.BookingService;
import org.example.design.service.ShowService;
import org.example.design.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.NonNull;


@Path("/v1/booking")
@Produces(MediaType.APPLICATION_JSON)
public class BookingResource {
	
	@Autowired
	private ShowService showService;
	
	@Autowired
	private TheatreService theatreService;
	
	@Autowired
	private BookingService bookingService;
	
	
	
	
    public String createBooking(@NonNull final String userId, @NonNull final String showId, @NonNull final List<String> seatsIds) {
        Show show = showService.getShow(showId);
        List<Seat> seats = seatsIds.stream().map(theatreService::getSeat).collect(Collectors.toList());
        return bookingService.createBooking(userId, show, seats).getId();
    }
	
}
