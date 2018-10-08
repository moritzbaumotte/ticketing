package com.baumotte.ticketing.endpoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/tickets")
public class TicketEP {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getTickets() {
		return "[{'id': '0'}, {'id: '1'}]";
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateTicket(){
		return "{'status': 'success'}";
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createTicket() {
		return "{'id': '0'}";
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteTicket() {
		return "{'status': 'success'}";
	}

}
