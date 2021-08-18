package org.example.design.service;

import java.util.ArrayList;
import java.util.List;

import org.example.design.model.Seat;
import org.example.design.model.Show;
import org.example.design.providers.SeatLockProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NonNull;


@Service
public class SeatAvailabilityService {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private SeatLockProvider seatLockProvider;
	
	
	
	
	public SeatAvailabilityService(@NonNull final BookingService bookingService, @NonNull final SeatLockProvider seatLockProvider) {
		this.bookingService = bookingService;
		this.seatLockProvider = seatLockProvider;
	}
	
	public List<Seat> getAvailableSeats(@NonNull final Show show) {
		final List<Seat> allSeats = show.getScreen().getSeats();
		final List<Seat> unavailableSeats = getUnavailableSeats(show);

		final List<Seat> availableSeats = new ArrayList<>(allSeats);
		availableSeats.removeAll(unavailableSeats);
		return availableSeats;
	}
	
	
	
	
	private List<Seat> getUnavailableSeats(@NonNull final Show show) {
		final List<Seat> unavailableSeats = bookingService.getBookedSeats(show);
		unavailableSeats.addAll(seatLockProvider.getLockedSeats(show));
		return unavailableSeats;
	}

}
