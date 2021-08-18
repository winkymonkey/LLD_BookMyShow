package org.example.design.providers;

import java.util.List;

import org.example.design.model.Seat;
import org.example.design.model.Show;


public interface SeatLockProvider {
    void lockSeats(Show show, List<Seat> seat, String user);
    void unlockSeats(Show show, List<Seat> seat, String user);
    boolean validateLock(Show show, Seat seat, String user);
    List<Seat> getLockedSeats(Show show);
}
