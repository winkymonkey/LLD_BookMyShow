package org.example.design.model;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class SeatLock {
	
	private Seat seat;
	private Show show;
	private Integer timeoutInSeconds;
	private Long lockTimeEpoch;
	private String lockedBy;
	
	
	
	
	public boolean isLockExpired() {
		return (lockTimeEpoch+timeoutInSeconds < Instant.now().getEpochSecond());
	}
	
}
