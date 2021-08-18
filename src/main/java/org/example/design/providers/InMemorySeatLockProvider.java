package org.example.design.providers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.design.exception.SeatTemporaryUnavailableException;
import org.example.design.model.Seat;
import org.example.design.model.SeatLock;
import org.example.design.model.Show;

import lombok.NonNull;


public class InMemorySeatLockProvider implements SeatLockProvider {

    private final Integer lockTimeout;
    private final Map<Show, Map<Seat, SeatLock>> locks;
    
    public InMemorySeatLockProvider(@NonNull final Integer lockTimeout) {
        this.locks = new HashMap<>();
        this.lockTimeout = lockTimeout;
    }
    
    
    
    
    
    @Override
    synchronized public void lockSeats(@NonNull final Show show, @NonNull final List<Seat> seats, @NonNull final String user) {
        for (Seat seat : seats) {
            if (isSeatLocked(show, seat)) {
                throw new SeatTemporaryUnavailableException();
            }
        }
        for (Seat seat : seats) {
            lockSeat(show, seat, user, lockTimeout);
        }
    }
    
    @Override
    public void unlockSeats(@NonNull final Show show, @NonNull final List<Seat> seats, @NonNull final String user) {
        for (Seat seat: seats) {
            if (validateLock(show, seat, user)) {
                unlockSeat(show, seat);
            }
        }
    }
    
    @Override
    public boolean validateLock(@NonNull final Show show, @NonNull final Seat seat, @NonNull final String user) {
        return isSeatLocked(show, seat) && locks.get(show).get(seat).getLockedBy().equals(user);
    }
    
    @Override
    public List<Seat> getLockedSeats(@NonNull final Show show) {
        if (!locks.containsKey(show)) {
            return Collections.unmodifiableList(new ArrayList<>());
        }
        final List<Seat> lockedSeats = new ArrayList<>();
        for (Seat seat : locks.get(show).keySet()) {
            if (isSeatLocked(show, seat)) {
                lockedSeats.add(seat);
            }
        }
        return Collections.unmodifiableList(lockedSeats);
    }
    
    
    
    
    
    private void unlockSeat(final Show show, final Seat seat) {
        if (!locks.containsKey(show)) {
            return;
        }
        locks.get(show).remove(seat);
    }
    
    private void lockSeat(final Show show, final Seat seat, final String user, final Integer timeoutInSeconds) {
        if (!locks.containsKey(show)) {
            locks.put(show, new HashMap<>());
        }
        final SeatLock lock = new SeatLock(seat, show, timeoutInSeconds, Instant.now().getEpochSecond(), user);
        locks.get(show).put(seat, lock);
    }
    
    private boolean isSeatLocked(final Show show, final Seat seat) {
        return locks.containsKey(show) && locks.get(show).containsKey(seat) && !locks.get(show).get(seat).isLockExpired();
    }
    
}
