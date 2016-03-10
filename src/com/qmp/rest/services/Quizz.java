package com.qmp.rest.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.qmp.rest.models.KQuestionnaire;
import com.qmp.rest.models.KReponse;

import net.ko.framework.KoHttp;
import net.ko.kobject.KListObject;



public class Quizz extends RestBase {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public String all() {
		return getAll();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public String getAll() {
		KListObject<KQuestionnaire> quizzes = KoHttp.getDao(KQuestionnaire.class).readAll();
		return gson.toJson(quizzes.asAL());
	}
	
	@Path("/{id}")
	public String getOne(int id){
		KQuestionnaire quizz = KoHttp.getDao(KQuestionnaire.class).readById(id);
		if (!quizz.isLoaded())
			return "null";
		return new Gson().toJson(quizz);
	}
}
