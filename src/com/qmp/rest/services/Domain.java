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
import com.qmp.rest.models.KQuestionnaire;

/**
 * @author Antoine
 * Domain REST Functions
 */
@Path("/domain")
public class Domain extends CrudRestBase {
	
	public Domain() {
		super();
		kobjectClass = KDomaine.class;
		displayName = "domain";
	}
	
}
