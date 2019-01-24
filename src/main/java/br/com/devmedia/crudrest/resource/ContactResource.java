package br.com.devmedia.crudrest.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import br.com.devmedia.crudrest.dao.ContactDAO;
import br.com.devmedia.crudrest.dao.IContactDAO;
import br.com.devmedia.crudrest.domain.Contact;

@Path("/contacts")
public class ContactResource {

	private static final Logger LOGGER = Logger.getLogger(ContactResource.class);

	private IContactDAO<Contact, Long> dao;

	public ContactResource() {
		this.dao = new ContactDAO();
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response save(Contact contact) {
		try {
		
			dao.save(contact);	
			
			return Response
					.status(200)
					.entity("Registro inserido: " + contact.toString())
					.build();	
			
		} catch (Exception ex) {
			LOGGER.error(ex);
			throw new WebApplicationException(500);
		}
	}

	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	public Response update(Contact contact) {
		try {
			
			dao.update(contact);
			
			return Response
					.status(200)
					.entity("Registro editado.")
					.build();	
			
		} catch (Exception ex) {
			LOGGER.error(ex);
			throw new WebApplicationException(500);
		}
	}

	@DELETE
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_JSON})
	public Response delete(@PathParam("id") Long id) {
		try {
			
			dao.delete(id);
			
			return Response
					.status(200)
					.entity("Registro removido.")
					.build();
			
		} catch (Exception ex) {
			LOGGER.error(ex);
			throw new WebApplicationException(500);
		}
	}

	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Contact findById(@PathParam("id") Long id) {
		try {

			return dao.findById(id);
			
		} catch (Exception ex) {
			LOGGER.error(ex);
			throw new WebApplicationException(500);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Contact> findAll() {
		try {
			
			return dao.findAll(); 
			
		} catch (Exception ex) {
			LOGGER.error(ex);
			throw new WebApplicationException(500);
		}
	}
	
	@GET
	@Path("/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByName(@PathParam("name") String name) {
		LOGGER.info("NAME : " + name);
		try {
			
			List<Contact> contacts = dao.findByName(name);
			
			GenericEntity<List<Contact>> entities = new GenericEntity<List<Contact>>(contacts) {};
			
			return Response
					.ok(entities)
					.build();
			
		} catch (Exception ex) {
			LOGGER.error(ex);
			throw new WebApplicationException(500);
		}
	}
}
