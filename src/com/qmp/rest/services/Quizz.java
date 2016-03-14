package com.qmp.rest.services;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.qmp.rest.models.KQuestionnaire;

import net.ko.framework.KoHttp;
import net.ko.kobject.KListObject;


/**
 * @author aleboisselier
 * Quizz REST Functions
 */
@Path("/quizz")
public class Quizz extends RestBase {
	/**
	 * Index Function
	 * @return All Quizzes Object in DB
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public String all() {
		return getAll();
	}
	
	/**
	 * Get all Quizzes Object in DB
	 * @return All Quizzes Object in DB
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public String getAll() {
		KListObject<KQuestionnaire> quizzes = KoHttp.getDao(KQuestionnaire.class).readAll();
		return gson.toJson(quizzes.asAL());
	}
	
	/**
	 * Search a quizz with given ID
	 * @param id of quizz to get
	 * @return Quizz JSON Object or NULL
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String getOne(@PathParam("id") int id){
		KQuestionnaire quizz = KoHttp.getDao(KQuestionnaire.class).readById(id);
		if (!quizz.isLoaded())
			return "null";
		return gson.toJson(quizz);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/user")
	public String getzazaz(@PathParam("id") int id){
		KQuestionnaire quizz = KoHttp.getDao(KQuestionnaire.class).readById(id);
		
		if (!quizz.isLoaded())
			return "null";
		return gson.toJson(quizz.getUtilisateur());
	}
	
	/**
	 * Add a quizz in DB using form passed in POST Request
	 * @param formParams POST form with quizz data
	 * @return Error or Success Message
	 * @throws SQLException
	 */
	/*@PUT
	@Path("/")
	@Consumes("application/x-www-form-urlencoded")
	public String addOne(MultivaluedMap<String, String> formParams)
			throws SQLException {
		KQuestionnaire quizzes = new KQuestionnaire();

		String message = "{\"message\": \"Insert OK\"}";
		
		String error = setValuesToKObject(quizzes, formParams);
		if(error != null)
			return error;
		
		KoHttp.getDao(KQuestionnaire.class).create(quizzes);
		return message;
	}*/
}
