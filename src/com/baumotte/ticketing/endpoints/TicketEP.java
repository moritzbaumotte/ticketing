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
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.baumotte.ticketing.entities.Service;
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
	public Response getTickets(@QueryParam("user") String email) throws JsonProcessingException {
		//get tickets
		if(email != null) {
			WebTarget target = client.target(getURL("dbconnector_ticketing"));
			ArrayList<Ticket> tickets = target
					.queryParam("user", email)
					.request()
					.accept(MediaType.APPLICATION_JSON)
					.get()
					.readEntity(ArrayList.class);
			
			return Response.status(Response.Status.OK).entity(tickets).build();
		}else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
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
	
	private String getURL(String serviceName) {
		return client.target("http://localhost:8080/servicebroker/rest/servicebroker")
				.queryParam("name", "dbconnector_ticketing")
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.get()
				.readEntity(Service.class)
				.getURL();
	}

}
