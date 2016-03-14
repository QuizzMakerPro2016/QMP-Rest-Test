package com.qmp.rest.services;

import javax.ws.rs.Path;

import com.qmp.rest.models.KQuestionnaire;


/**
 * @author aleboisselier
 * Quizz REST Functions
 */
@Path("/quizz")
public class Quizz extends CrudRestBase {	
	public Quizz() {
		super();
		kobjectClass = KQuestionnaire.class;
		displayName = "quizz";

	}
}
