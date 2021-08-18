package org.example.design.resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.example.design.service.BookingService;
import org.example.design.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.NonNull;


@Path("/v1/payments")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentsResource {
	
	@Autowired
	private PaymentsService paymentsService;
	
	@Autowired
    private BookingService bookingService;
	
	
	
	
    public void paymentFailed(@NonNull final String bookingId, @NonNull final String user) {
        paymentsService.processPaymentFailed(bookingService.getBooking(bookingId), user);
    }

    public void paymentSuccess(@NonNull final  String bookingId, @NonNull final String user) {
        bookingService.confirmBooking(bookingService.getBooking(bookingId), user);
    }
    
}
