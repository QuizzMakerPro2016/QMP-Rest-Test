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

import com.google.gson.Gson;
import com.qmp.rest.models.KReponse;

import net.ko.framework.KoHttp;
import net.ko.kobject.KListObject;



/**
 * @author aleboisselier
 * Answers REST Functions
 */
@Path("/answer")
public class Answer extends RestBase{

	/**
	 * Return all answers
	 * @return JSON List of all answers
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public String index() {
		return getAll();
	}

	
	/**
	 * Return all answers
	 * @return JSON List of all answers
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public String getAll() {
		KListObject<KReponse> answers = KoHttp.getDao(KReponse.class).readAll();
		return new Gson().toJson(answers.asAL());
	}
	
	/**
	 * Get answer by ID
	 * @param id - Searched Answer's id
	 * @return JSON Answer with id parameter
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String getOne(@PathParam("id") int id) {
		KReponse answer = KoHttp.getDao(KReponse.class).readById(id);
		if (!answer.isLoaded())
			return "null";
		return new Gson().toJson(answer);
	}
	

	/**
	 * Add an answer in DB using form passed in POST Request
	 * @param formParams POST form with answer data
	 * @return Error or Success Message
	 * @throws SQLException
	 */
	@PUT
	@Path("/")
	@Consumes("application/x-www-form-urlencoded")
	public String addOne(MultivaluedMap<String, String> formParams)
			throws SQLException {
		KReponse answer = new KReponse();

		String message = "{\"message\": \"Insert OK\"}";
		
		String error = setValuesToKObject(answer, formParams);
		if(error != null)
			return error;
		
		KoHttp.getDao(KReponse.class).create(answer);
		return message;
	}
	
	/**
	 * Update an answer in DB using form passed in POST Request
	 * @param formParams POST form with answer data
	 * @return Error or Success Message
	 * @throws SQLException
	 */
	@POST
	@Path("/update")
	@Consumes("application/x-www-form-urlencoded")
	public String updateAnswer(MultivaluedMap<String, String> formParams)
			throws SQLException {
		int id = Integer.valueOf(formParams.get("id").get(0));
		KReponse answer = KoHttp.getDao(KReponse.class).readById(id);
		
		if (!answer.isLoaded())
			return "{\"message\": \"Error while loading Answer with id " + String.valueOf(id) + "\"}";

		String message = "{\"message\": \"Update OK\"}";
		
		String error = setValuesToKObject(answer, formParams);
		if(error != null)
			return error;
		KoHttp.getDao(KReponse.class).update(answer);
		
		return message;
	}
	
	/**
	 * Delete an answer entry in DB
	 * @param id - ID of Answer to delete
	 * @return Error or Success Message
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String deleteAnswer(@PathParam("id") int id){
		KReponse answer = KoHttp.getDao(KReponse.class).readById(id);
		String message = "{\"message\": \"Error while loading Answer with id " + String.valueOf(id) + "\"}";
		if (!answer.isLoaded())
			return message;
		try {
			KoHttp.getDao(KReponse.class).delete(answer);
		} catch (SQLException e) {
			message = "{\"message\": \" "+e.getMessage()+"\"}";
		}
		message="{\"message\": \"Delete OK\"}";
		
		return message;
	}

}
