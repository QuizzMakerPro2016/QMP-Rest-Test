package com.qmp.rest.services;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import net.ko.framework.KoHttp;
import net.ko.kobject.KListObject;

import com.google.gson.Gson;
import com.qmp.rest.models.KDomaine;

/**
 * @author Antoine
 * Domain REST Functions
 */
@Path("/domain")
public class Domain extends RestBase {
	
	/**
	 * Return all Domains
	 * @return JSON Domains List
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public String index() {
		return getAll();
	}

	/**
	 * Return all Domains
	 * @return JSON Domains List
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public String getAll() {
		KListObject<KDomaine> domains = KoHttp.getDao(KDomaine.class).readAll();
		return gson.toJson(domains.asAL());
	}
	
	
	/**
	 * Get domain by ID
	 * @param id - Searched Domain's id
	 * @return JSON Domain with id parameter
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String getOne(@PathParam("id") int id) {
		KDomaine domain = KoHttp.getDao(KDomaine.class).readById(id);
		if (!domain.isLoaded())
			return "null";
		return gson.toJson(domain);
	}
	
	/**
	 * Get quizzes about the domain of ID id
	 * @param id - Quizz ID
	 * @return JSON Domain List
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/quizzes")
	public String getQuizzes(@PathParam("id") int id) {
		KDomaine domain = KoHttp.getDao(KDomaine.class).readById(id);
		if (!domain.isLoaded())
			return "null";
		
		return gson.toJson(domain.getQuestionnaires().asAL());
	}
	
	/**
	 * Add a domain in DB using form passed in POST Request
	 * @param formParams POST form with domain data
	 * @return Error or Success Message
	 * @throws SQLException
	 */
	@PUT
	@Path("/")
	@Consumes("application/x-www-form-urlencoded")
	public String addOne(MultivaluedMap<String, String> formParams)
			throws SQLException {
		KDomaine domain = new KDomaine();

		String message = "{\"message\": \"Insert OK\"}";
		
		String error = setValuesToKObject(domain, formParams);
		if(error != null)
			return error;
		
		KoHttp.getDao(KDomaine.class).create(domain);
		return message;
	}
	
	/**
	 * Update a domain in DB using form passed in POST Request
	 * @param formParams POST form with domain data
	 * @return Error or Success Message
	 * @throws SQLException
	 */
	@POST
	@Path("/update")
	@Consumes("application/x-www-form-urlencoded")
	public String updateDomain(MultivaluedMap<String, String> formParams)
			throws SQLException {
		int id = Integer.valueOf(formParams.get("id").get(0));
		KDomaine domain = KoHttp.getDao(KDomaine.class).readById(id);
		
		if (!domain.isLoaded())
			return "{\"message\": \"Error while loading Domain with id " + String.valueOf(id) + "\"}";

		String message = "{\"message\": \"Update OK\"}";
		
		String error = setValuesToKObject(domain, formParams);
		if(error != null)
			return error;
		
		KoHttp.getDao(KDomaine.class).update(domain);
		
		return message;
	}
	
	/**
	 * Delete a Domain entry in DB
	 * @param id - ID of Domain to delete
	 * @return Error or Success Message
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String deleteDomain(@PathParam("id") int id){
		KDomaine domain = KoHttp.getDao(KDomaine.class).readById(id);
		String message = "{\"message\": \"Error while loading Domain with id " + String.valueOf(id) + " \"}";
		
		if (!domain.isLoaded())
			return message;
		
		try {
			KoHttp.getDao(KDomaine.class).delete(domain);
		} catch (SQLException e) {
			message = "{\"message\": \" "+e.getMessage()+"\"}";
		}
		message="{\"message\": \"Delete OK\"}";
		
		return message;
	}

}
