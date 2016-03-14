package com.qmp.rest.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.ko.framework.Ko;
import net.ko.framework.KoSession;
import net.ko.kobject.KListObject;
import com.qmp.rest.models.KGroupe;
import com.qmp.rest.models.KQuestionnaire;
import com.qmp.rest.models.KUtilisateur;

@Path("/user")
public class User extends CrudRestBase {

	public User() {
		super();
		kobjectClass = KUtilisateur.class;
		displayName = "user";
	}

	@GET
	@Path("/{id}/quiz")
	@Produces(MediaType.APPLICATION_JSON)
	public String getQuiz(@PathParam("id") int id) {
		Ko.setTempConstraintDeph(2);
		KUtilisateur user = KoSession.kloadOne(KUtilisateur.class, id);
		KListObject<KQuestionnaire> quizes = new KListObject<>(KQuestionnaire.class);
		KListObject<KGroupe> groupes = user.getGroupes();
		for (KGroupe gr : groupes) {
			quizes.addAll(gr.getQuestionnaires());
		}
		String result = gson.toJson(quizes.asAL());
		Ko.restoreConstraintDeph();
		return result;
	}

	@POST
	@Path("/connect")
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public String connect(@FormParam("login") String login, @FormParam("password") String password) {
		KUtilisateur user = KoSession.kloadOne(KUtilisateur.class, "login='" + login + "'");
		String result = returnMessage("Login ou mot de passe invalides", true);

		if (user.isLoaded()) {
			if (user.getPassword().equals(password)) {
				result = returnValue("Connexion réussie de " + user, "utilisateur", user, "\"connected\":true");
			}
		}
		return result;
	}
}