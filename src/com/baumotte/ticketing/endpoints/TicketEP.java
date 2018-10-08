package com.baumotte.ticketing.endpoints;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/tickets")
public class TicketEP {
	
	@GET
	//@Produces(MediaType.TEXT_PLAIN)
	public String getTickets() {
		return "hello world";
	}

}
