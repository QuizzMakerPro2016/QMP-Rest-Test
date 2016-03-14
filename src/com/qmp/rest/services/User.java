package com.qmp.rest.services;



import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.qmp.rest.models.KGroupe;
import com.qmp.rest.models.KQuestionnaire;
import com.qmp.rest.models.KUtilisateur;

import net.ko.framework.Ko;
import net.ko.framework.KoHttp;
import net.ko.framework.KoSession;
import net.ko.kobject.KListObject;


@Path("/user")
public class User extends RestBase {

	/**
	 * Return all Users (root)
	 * @return JSON User List
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public String root() {
		KListObject<KUtilisateur> users = KoHttp.getDao(KUtilisateur.class).readAll();
		return gson.toJson(users.asAL());
	}
	
	/**
	 * Return all Users
	 * @return JSON User List
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public String all() {
		KListObject<KUtilisateur> users = KoHttp.getDao(KUtilisateur.class).readAll();
		return gson.toJson(users.asAL());
	}

	/**
	 * Return a Users
	 * @return JSON User
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String getOne(@PathParam("id") int id) {
		KUtilisateur user = KoHttp.getDao(KUtilisateur.class).readById(id);
		if (!user.isLoaded())
			return "null";
		return gson.toJson(user);
	}
	
	

	/**
	 * Return all quizzes which can be answered by a user
	 * @return JSON quizz List
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/quizzes")
	public String quizzes(@PathParam("id") int id) {
		Ko.setTempConstraintDeph(2);
		KUtilisateur user = KoSession.kloadOne(KUtilisateur.class, id);
		KListObject<KQuestionnaire> quizes = new KListObject<KQuestionnaire>(KQuestionnaire.class);
		KListObject<KGroupe> groupes = user.getGroupes();
		for (KGroupe gr : groupes) {
			quizes.addAll(gr.getQuestionnaires());
		}
		String result = gson.toJson(quizes.asAL());
		Ko.restoreConstraintDeph();
		return result;
	}

	/**
	 * Return all quizzes done by a Users
	 * @return JSON quizz List
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/quizzes/done")
	public String quizzesDone(@PathParam("id") int id) {
		KUtilisateur user = KoSession.kloadOne(KUtilisateur.class, id);
		String result = gson.toJson(user.getRealisations().asAL());
		return result;
	}

	/**
	 * Return all groups of a user
	 * @return JSON group List
	 */
	@GET
	@Path("/{id}/groups")
	@Produces(MediaType.APPLICATION_JSON)
	public String getGroups(@PathParam("id") int id) {
		KUtilisateur user = KoSession.kloadOne(KUtilisateur.class, id);
		String result = gson.toJson(user.getGroupes().asAL());
		return result;
	}
	
	/**
	 * Return all quizz made by a user
	 * @return JSON quizz List
	 */
	@GET
	@Path("/{id}/quizzes/made")
	@Produces(MediaType.APPLICATION_JSON)
	public String quizzMade(@PathParam("id") int id) {
		KUtilisateur user = KoSession.kloadOne(KUtilisateur.class, id);
		String result = gson.toJson(user.getQuestionnaires());
		return result;
	}
	
	/**
	 * Return all questions made by a user
	 * @return JSON question List
	 */
	@GET
	@Path("/{id}/questions/made")
	@Produces(MediaType.APPLICATION_JSON)
	public String questionMade(@PathParam("id") int id) {
		KUtilisateur user = KoSession.kloadOne(KUtilisateur.class, id);
		String result = gson.toJson(user.getQuestions());
		return result;
	}

	/**
	 * Connect a User
	 * @return String result
	 */
	@POST
	@Path("/connect")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public String connect(@FormParam("login") String login, @FormParam("password") String password) {
		KUtilisateur user = KoSession.kloadOne(KUtilisateur.class, "login='" + login + "'");
		String result = "{\"connected\":false,\"message\":\"Nom d'utilisateur ou mot de passe incorrect\"}";

		if (user.isLoaded()) {
			if (user.getPassword().equals(password)) {
				result = "{\"connected\":true,\"user\":" + gson.toJson(user) + "}";
			}
		}
		return result;
	}

	/**
	 * Update a User
	 * @return String message
	 */
	@POST
	@Path("/{id}")
	@Consumes("application/x-www-form-urlencoded")
	public String update(MultivaluedMap<String, String> formParams, @PathParam("id") int id)
			throws SQLException {
		KUtilisateur user = KoHttp.getDao(KUtilisateur.class).readById(id);
		
		if (!user.isLoaded())
			return "{\"message\": \"Error while loading user with id " + String.valueOf(id) + "\"}";

		String message = "{\"message\": \"Update OK\"}";
		
		String error = setValuesToKObject(user, formParams);
		if(error != null)
			return error;

		KoHttp.getDao(KUtilisateur.class).update(user);
		
		return message;
	}

	/**
	 * Create a User
	 * @return String message
	 */
	@PUT
	@Path("/")
	@Consumes("application/x-www-form-urlencoded")
	public String addGroup(MultivaluedMap<String, String> formParams)
			throws SQLException {
		KUtilisateur user = new KUtilisateur();
	

		String message = "{\"message\": \"Added new user OK\"}";
		
		String error = setValuesToKObject(user, formParams);
		if(error != null)
			return error;

		KoHttp.getDao(KUtilisateur.class).create(user);
		
		return message;
	}

	/**
	 * Delete a user
	 * @return String message
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String delete(@PathParam("id") int id){
		KUtilisateur user = KoHttp.getDao(KUtilisateur.class).readById(id);
		String message = "{\"message\": \"Delete FAILED\"}";
		if (!user.isLoaded())
			return message;
		try {
			KoHttp.getDao(KUtilisateur.class).delete(user);
		} catch (SQLException e) {
			message = "{\"message\": \" "+e.getMessage()+"\"}";
		}
		message="{\"message\": \"Delete OK\"}";
		
		return message;
	}

}