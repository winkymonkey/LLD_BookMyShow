package org.example.design.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.example.design.exception.NotFoundException;
import org.example.design.model.Screen;
import org.example.design.model.Seat;
import org.example.design.model.Theatre;
import org.springframework.stereotype.Service;

import lombok.NonNull;


@Service
public class TheatreService {

    private final Map<String, Theatre> theatres = new HashMap<>();
    private final Map<String, Screen> screens = new HashMap<>();
    private final Map<String, Seat> seats = new HashMap<>();
    
    
    
    
    
	public Theatre getTheatre(@NonNull final String theatreId) {
        if (!theatres.containsKey(theatreId)) {
            throw new NotFoundException();
        }
        return theatres.get(theatreId);
    }
    
    public Screen getScreen(@NonNull final String screenId) {
        if (!screens.containsKey(screenId)) {
            throw new NotFoundException();
        }
        return screens.get(screenId);
    }
    
    public Seat getSeat(@NonNull final String seatId) {
        if (!seats.containsKey(seatId)) {
            throw new NotFoundException();
        }
        return seats.get(seatId);
    }
    
    
    
    
    
	public Theatre createTheatre(@NonNull final String theatreName) {
        String theatreId = UUID.randomUUID().toString();
        Theatre theatre = new Theatre(theatreId, theatreName);
        theatres.put(theatreId, theatre);
        return theatre;
    }
    
    public Screen attachScreenInTheatre(@NonNull final String screenName, @NonNull final Theatre theatre) {
        Screen screen = createScreen(screenName, theatre);
        theatre.addScreen(screen);
        return screen;
    }
    
    public Seat attachSeatInScreen(@NonNull final Integer rowNo, @NonNull final Integer seatNo, @NonNull final Screen screen) {
        Seat seat = createSeat(rowNo, seatNo);
        screen.addSeat(seat);
        return seat;
	}
    
    
    
    
    
	private Screen createScreen(final String screenName, final Theatre theatre) {
        String screenId = UUID.randomUUID().toString();
        Screen screen = new Screen(screenId, screenName, theatre);
        screens.put(screenId, screen);
        return screen;
    }
    
    private Seat createSeat(@NonNull final Integer rowNo, @NonNull final Integer seatNo) {
    	String seatId = UUID.randomUUID().toString();
        Seat seat = new Seat(seatId, rowNo, seatNo);
        seats.put(seatId, seat);
        return seat;
    }
    
}
