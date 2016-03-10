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

import net.ko.framework.Ko;
import net.ko.framework.KoHttp;
import net.ko.framework.KoSession;
import net.ko.kobject.KListObject;

import com.qmp.rest.models.KGroupe;
import com.qmp.rest.models.KQuestionnaire;
import com.qmp.rest.models.KUtilisateur;


@Path("/user")
public class User extends RestBase {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public String all() {
		KListObject<KUtilisateur> users = KoHttp.getDao(KUtilisateur.class).readAll();
		return gson.toJson(users.asAL());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String getOne(@PathParam("id") int id) {
		KUtilisateur user = KoHttp.getDao(KUtilisateur.class).readById(id);
		if (!user.isLoaded())
			return "null";
		return gson.toJson(user);
	}

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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/quizzes/done")
	public String quizzesDone(@PathParam("id") int id) {
		KUtilisateur user = KoSession.kloadOne(KUtilisateur.class, id);
		String result = gson.toJson(user.getRealisations().asAL());
		return result;
	}

	@GET
	@Path("/recovery/{mail}")
	public String recovery() {
		return null;
		/* Todo */

	}

	@GET
	@Path("/{id}/groups")
	@Produces(MediaType.APPLICATION_JSON)
	public String getGroups(@PathParam("id") int id) {
		KUtilisateur user = KoSession.kloadOne(KUtilisateur.class, id);
		String result = gson.toJson(user.getGroupes().asAL());
		return result;
	}

	@GET
	@Path("/checkConnected")
	public String checkConnected() {
		return null;
		/* Todo */
	}

	@POST
	@Path("/update/{id}")
	@Consumes("application/x-www-form-urlencoded")
	public String update(MultivaluedMap<String, String> formParams, @PathParam("id") int id)
			throws SQLException {
		KUtilisateur user = KoHttp.getDao(KUtilisateur.class).readById(id);
		
		if (!user.isLoaded())
			return "{\"message\": \"Error while loading group with id " + String.valueOf(id) + "\"}";

		String message = "{\"message\": \"Update OK\"}";
		
		String error = setValuesToKObject(user, formParams);
		if(error != null)
			return error;

		KoHttp.getDao(KUtilisateur.class).update(user);
		
		return message;
	}

	@PUT
	@Path("/add")
	@Consumes("application/x-www-form-urlencoded")
	public String addGroup(MultivaluedMap<String, String> formParams)
			throws SQLException {
		KUtilisateur user = new KUtilisateur();
		
		if (!user.isLoaded())
			return "{\"message\": \"Error while creating group \"}";

		String message = "{\"message\": \"Adding new group OK\"}";
		
		String error = setValuesToKObject(user, formParams);
		if(error != null)
			return error;

		KoHttp.getDao(KUtilisateur.class).create(user);
		
		return message;
	}

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