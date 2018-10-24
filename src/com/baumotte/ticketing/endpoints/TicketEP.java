package com.baumotte.ticketing.endpoints;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientResponse;

import com.baumotte.ticketing.entities.Service;
import com.baumotte.ticketing.entities.Ticket;

import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/tickets")
public class TicketEP {
	private final Client client;
	
	public TicketEP() {
		this.client = ClientBuilder.newClient();
	}
	
	@GET
	@Path("/{user}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTickets(@PathParam("user") String email) {
		//get tickets
		if(email != null) {
			WebTarget target = client.target(getURL("dbconnector_ticketing") + "/" + email + "/tickets");
			ArrayList<Ticket> tickets = target
					.request()
					.accept(MediaType.APPLICATION_JSON)
					.get()
					.readEntity(ArrayList.class);
			
			return Response.status(Response.Status.OK).entity(tickets).build();
		}else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/{user}/ticket/{id}/responses")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResponses(@PathParam("user") String email, @PathParam("id") int id) {
		if(email != null) {
			WebTarget target = client.target(getURL("dbconnector_ticketing") + "/" + email + "/tickets/" + id + "/responses");
			ArrayList<com.baumotte.ticketing.entities.Response> responses = target
					.request()
					.accept(MediaType.APPLICATION_JSON)
					.get()
					.readEntity(ArrayList.class);
			
			return Response.status(Response.Status.OK).entity(responses).build();
		}else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@POST
	@Path ("/{user}/ticket")
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response createTicket(Ticket ticket, @PathParam("user") String email) {
		Response r;
		
		if(email != null) {
			WebTarget target = client.target(getURL("dbconnector_ticketing") + "/" + email + "/tickets");
			int id = target.request().post(Entity.entity(ticket, MediaType.APPLICATION_JSON)).readEntity(int.class);
			r = Response.status(Response.Status.OK).entity(id).build();
		}else {
			r = Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		return r;
	}
	
	@POST
	@Path ("/{user}/ticket/{id}/responses")
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response createResponse(com.baumotte.ticketing.entities.Response response, @PathParam("user") String email, @PathParam("id") int id) {
		Response r;
		
		if(email != null && id != 0) {
			WebTarget target = client.target(getURL("dbconnector_ticketing") + "/" + email + "/tickets/" + id + "/responses");
			int respId = target.request().post(Entity.entity(response, MediaType.APPLICATION_JSON)).readEntity(int.class);
			r = Response.status(Response.Status.OK).entity(respId).build();
		}else {
			r = Response.status(Response.Status.BAD_REQUEST).build();
		}
		
		return r;
	}
	
	@DELETE
	@Path ("/{user}/ticket/{id}")
	@Consumes (MediaType.APPLICATION_JSON)
	public void deleteTicket(@PathParam("user") String email, @PathParam("id") int id) {
		if(email != null && id != 0) {
			WebTarget target = client.target(getURL("dbconnector_ticketing") + "/" + email + "/tickets/" + id);
			target.request().delete();
		}
	}
	
	private String getURL(String serviceName) {
		return client.target("http://localhost:8081/servicebroker/rest/servicebroker/" + serviceName)
				.request()
				.accept(MediaType.APPLICATION_JSON)
				.get()
				.readEntity(Service.class)
				.getURL();
	}

}
