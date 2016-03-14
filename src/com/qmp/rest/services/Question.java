/**
 * 
 */
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
import net.ko.framework.KoSession;
import net.ko.kobject.KListObject;

import com.qmp.rest.models.KGroupe;
import com.qmp.rest.models.KQuestion;
import com.qmp.rest.models.KQuestion_questionnaire;
import com.qmp.rest.models.KQuestionnaire;


/**
 * @author aleboisselier
 *
 */
@Path("/question")
public class Question extends CrudRestBase {
	public Question() {
		kobjectClass = KGroupe.class;
		displayName = "question";
	}
}
