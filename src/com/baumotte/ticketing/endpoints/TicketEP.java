package com.baumotte.ticketing.endpoints;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.baumotte.ticketing.entities.Ticket;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/tickets")
public class TicketEP {
	private final ObjectMapper mapper;
	
	public TicketEP() {
		this.mapper = new ObjectMapper();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getTickets() throws JsonProcessingException {
		Ticket dummyTicket = new Ticket(0, "moritz@baumotte.com", "test title", "test description");
		//get tickets
		return mapper.writeValueAsString(dummyTicket);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateTicket(String ticketJson) throws JsonParseException, JsonMappingException, IOException{
		Ticket ticket = mapper.readValue(ticketJson, Ticket.class);
		//update ticket
		return mapper.writeValueAsString(ticket);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String createTicket(String ticketJson) throws JsonParseException, JsonMappingException, IOException {
		Ticket ticket = mapper.readValue(ticketJson, Ticket.class);
		//create ticket
		return "{'status': 'success'}";
	}
	
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteTicket(String ticketJson) throws JsonParseException, JsonMappingException, IOException {
		Ticket ticket = mapper.readValue(ticketJson, Ticket.class);
		//mark ticket as complete
		return "{'status': 'success'}";
	}

}
