package org.example.design.resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.example.design.model.Screen;
import org.example.design.model.Theatre;
import org.example.design.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.NonNull;


@Path("/v1/theatre")
@Produces(MediaType.APPLICATION_JSON)
public class TheatreResource {
	
	@Autowired
	private TheatreService theatreService;
	
	
    public String createTheatre(@NonNull final String theatreName) {
        return theatreService.createTheatre(theatreName).getId();
    }

    public String attachScreenInTheatre(@NonNull final String screenName, @NonNull final String theatreId) {
        Theatre theatre = theatreService.getTheatre(theatreId);
        return theatreService.attachScreenInTheatre(screenName, theatre).getId();
    }

    public String attachSeatInScreen(@NonNull final Integer rowNo, @NonNull final Integer seatNo, @NonNull final String screenId) {
        Screen screen = theatreService.getScreen(screenId);
        return theatreService.attachSeatInScreen(rowNo, seatNo, screen).getId();
    }
    
}
