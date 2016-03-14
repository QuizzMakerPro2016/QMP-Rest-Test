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

import com.qmp.rest.models.KQuestion;
import com.qmp.rest.models.KQuestion_questionnaire;
import com.qmp.rest.models.KQuestionnaire;


/**
 * @author aleboisselier
 *
 */
@Path("/question")
public class Question extends RestBase {
	
	/**
	 * Index Function
	 * @return JSON List of all questions
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public String index() {
		return getAll();
	}

	
	/**
	 * Return all questions
	 * @return JSON List of all questions
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public String getAll() {
		KListObject<KQuestion> questions = KoSession.kloadMany(KQuestion.class);
		return gson.toJson(questions);
	}
	
	/**
	 * Return Question of ID 'id'
	 * @return JSON Question Object
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String getOne(@PathParam("id") int id) {
		KQuestion question = KoSession.kloadOne(KQuestion.class, id);
		if(!question.isLoaded())
			return "{\"message\": \"Error while loading Question with id " + String.valueOf(id) + "\"}";
		
		return gson.toJson(question);
	}
	
	/**
	 * Add a question in DB using form passed in POST Request
	 * @param formParams POST form with question data
	 * @return Error or Success Message
	 * @throws SQLException
	 */
	@PUT
	@Path("/")
	@Consumes("application/x-www-form-urlencoded")
	public String addOne(MultivaluedMap<String, String> formParams)
			throws SQLException {
		KQuestion question = new KQuestion();
		
		String error = setValuesToKObject(question, formParams);
		if(error != null)
			return error;
		
		KoHttp.getDao(KQuestion.class).create(question);
		return "{\"message\": \"Insert OK\"}";
	}
	
	/**
	 * Update a question in DB using form passed in POST Request
	 * @param formParams POST form with question data
	 * @return Error or Success Message
	 * @throws SQLException
	 */
	@POST
	@Path("/update")
	@Consumes("application/x-www-form-urlencoded")
	public String updateQuestion(MultivaluedMap<String, String> formParams)
			throws SQLException {
		int id = Integer.valueOf(formParams.get("id").get(0));
		KQuestion question = KoHttp.getDao(KQuestion.class).readById(id);
		
		if (!question.isLoaded())
			return "{\"message\": \"Error while loading Answer with id " + String.valueOf(id) + "\"}";
		
		String error = setValuesToKObject(question, formParams);
		if(error != null)
			return error;
		KoHttp.getDao(KQuestion.class).update(question);
		
		return "{\"message\": \"Update OK\"}";
	}
	
	/**
	 * Delete a question entry in DB
	 * @param id - ID of Question to delete
	 * @return Error or Success Message
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String deleteQuestion(@PathParam("id") int id){
		KQuestion question = KoHttp.getDao(KQuestion.class).readById(id);
		if (!question.isLoaded())
			return "{\"message\": \"Error while loading Answer with id " + String.valueOf(id) + "\"}";
		
		try {
			KoHttp.getDao(KQuestion.class).delete(question);
		} catch (SQLException e) {
			return "{\"message\": \" "+e.getMessage()+"\"}";
		}
		
		return "{\"message\": \"Delete OK\"}";
	}
	
	/**
	 * Return all Quizzes containing Question with ID 'id'
	 * @return JSON List of all questions
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/quizzes")
	public String getQuizes(@PathParam("id") int id) {
		KListObject<KQuestion_questionnaire> question = KoSession.kloadMany(KQuestion_questionnaire.class, "idQuestion="+id);
		KListObject<KQuestionnaire> quizzes = new KListObject<KQuestionnaire>(KQuestionnaire.class);
		for(KQuestion_questionnaire q : question){
			quizzes.add(q.getQuestionnaire());
		}
		return gson.toJson(quizzes);
	}
	
	/**
	 * Return all Answers of Question with ID 'id'
	 * @return JSON List of all questions
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}/answers")
	public String getAnswers(@PathParam("id") int id) {
		KQuestion question = KoSession.kloadOne(KQuestion.class, id);
		if(!question.isLoaded())
			return "{\"message\": \"Error while loading Question with id " + String.valueOf(id) + "\"}";
		
		return gson.toJson(question.getReponses());
	}
	

}
