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
import com.qmp.rest.models.KGroupe;
import com.qmp.rest.models.KReponse;

import net.ko.framework.KoHttp;
import net.ko.kobject.KListObject;



/**
 * @author aleboisselier
 * Answers REST Functions
 */
@Path("/answer")
public class Answer extends CrudRestBase {
		
	public Answer() {
		kobjectClass = KReponse.class;
		displayName = "answer";
	}
	
}
