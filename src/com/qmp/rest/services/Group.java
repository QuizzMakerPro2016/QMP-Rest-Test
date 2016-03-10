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

import com.qmp.rest.models.KGroupe;

@Path("/group")
public class Group extends RestBase {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public String all() {
		KListObject<KGroupe> groups = KoHttp.getDao(KGroupe.class).readAll();
		return gson.toJson(groups.asAL());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String getOne(@PathParam("id") int id) {
		KGroupe group = KoHttp.getDao(KGroupe.class).readById(id);
		if (!group.isLoaded())
			return "null";
		return gson.toJson(group);
	}

	@GET
	@Path("/{id}/quizzes")
	public String quizzes(@PathParam("id") int id) {
		return "";
	}

	@GET
	@Path("/{id}/users")
	public String users() {
		return null;
	}
	
	@PUT
	@Path("/add")
	@Consumes("application/x-www-form-urlencoded")
	public String addGroup(MultivaluedMap<String, String> formParams)
			throws SQLException {
		KGroupe group = new KGroupe();
		
		if (!group.isLoaded())
			return "{\"message\": \"Error while creating group \"}";

		String message = "{\"message\": \"Adding new group OK\"}";
		
		String error = setValuesToKObject(group, formParams);
		if(error != null)
			return error;

		KoHttp.getDao(KGroupe.class).create(group);
		
		return message;
	}
	
	@POST
	@Path("/update/{id}")
	@Consumes("application/x-www-form-urlencoded")
	public String update(MultivaluedMap<String, String> formParams, @PathParam("id") int id)
			throws SQLException {
		KGroupe group = KoHttp.getDao(KGroupe.class).readById(id);
		
		if (!group.isLoaded())
			return "{\"message\": \"Error while loading group with id " + String.valueOf(id) + "\"}";

		String message = "{\"message\": \"Update OK\"}";
		
		String error = setValuesToKObject(group, formParams);
		if(error != null)
			return error;

		KoHttp.getDao(KGroupe.class).update(group);
		
		return message;
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String delete(@PathParam("id") int id){
		KGroupe group = KoHttp.getDao(KGroupe.class).readById(id);
		String message = "{\"message\": \"Delete FAILED\"}";
		if (!group.isLoaded())
			return message;
		try {
			KoHttp.getDao(KGroupe.class).delete(group);
		} catch (SQLException e) {
			message = "{\"message\": \" "+e.getMessage()+"\"}";
		}
		message="{\"message\": \"Delete OK\"}";
		
		return message;
	}

}