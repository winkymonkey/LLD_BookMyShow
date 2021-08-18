package org.example.design.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.example.design.exception.BadRequestException;
import org.example.design.exception.NotFoundException;
import org.example.design.exception.SeatPermanentlyUnavailableException;
import org.example.design.model.Booking;
import org.example.design.model.Seat;
import org.example.design.model.Show;
import org.example.design.providers.InMemorySeatLockProvider;
import org.example.design.providers.SeatLockProvider;
import org.springframework.stereotype.Service;

import lombok.NonNull;


@Service
public class BookingService {

    private final SeatLockProvider seatLockProvider = new InMemorySeatLockProvider(180);
    private final Map<String, Booking> bookings = new HashMap<>();
    
    
    
    
    
	public Booking getBooking(@NonNull final String bookingId) {
		if (!bookings.containsKey(bookingId)) {
			throw new NotFoundException();
		}
		return bookings.get(bookingId);
	}
	
	public List<Booking> getAllBookings(@NonNull final Show show) {
		List<Booking> response = new ArrayList<>();
		for (Booking booking : bookings.values()) {
			if (booking.getShow().equals(show)) {
				response.add(booking);
			}
		}
		return response;
	}
	
	public Booking createBooking(@NonNull final String userId, @NonNull final Show show, @NonNull final List<Seat> seats) {
		if (isSeatAlreadyBooked(show, seats)) {
			throw new SeatPermanentlyUnavailableException();
		}
		seatLockProvider.lockSeats(show, seats, userId);
		String bookingId = UUID.randomUUID().toString();
		Booking newBooking = new Booking(bookingId, show, userId, seats);
		bookings.put(bookingId, newBooking);
		return newBooking;
	}
    
	public List<Seat> getBookedSeats(@NonNull final Show show) {
		return getAllBookings(show).stream().filter(Booking::isConfirmed)
											.map(Booking::getSeatsBooked)
											.flatMap(Collection::stream)
											.collect(Collectors.toList());
	}
    
	public void confirmBooking(@NonNull final Booking booking, @NonNull final String user) {
		if (!booking.getUser().equals(user)) {
			throw new BadRequestException();
		}
		for (Seat seat : booking.getSeatsBooked()) {
			if (!seatLockProvider.validateLock(booking.getShow(), seat, user)) {
				throw new BadRequestException();
			}
		}
		booking.confirmBooking();
	}
	
	
	
	
	
	private boolean isSeatAlreadyBooked(final Show show, final List<Seat> seats) {
		List<Seat> bookedSeats = getBookedSeats(show);
		for (Seat seat : seats) {
			if (bookedSeats.contains(seat)) {
				return true;
			}
		}
		return false;
	}
    
}
