package com.baumotte.ticketing.endpoints;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.baumotte.ticketing.entities.Ticket;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/tickets")
public class TicketEP {
	private final ObjectMapper mapper;
	private final Client client;
	
	public TicketEP() {
		this.mapper = new ObjectMapper();
		this.client = ClientBuilder.newClient();
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTickets() throws JsonProcessingException {
		//Ticket dummyTicket = new Ticket(0, "moritz@baumotte.com", "test title", "test description");
		//get tickets
		WebTarget target = client.target("http://localhost:8080/dbconnector/rest/queries");
		ArrayList<Ticket> tickets = target.request().accept("application/json").get().readEntity(ArrayList.class);
		
		return Response.status(Response.Status.OK).entity(tickets).build();
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
