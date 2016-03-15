package quiz.rest.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import net.ko.framework.Ko;
import net.ko.framework.KoSession;
import net.ko.kobject.KListObject;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import qcm.utils.HttpUtils;
import qcm.utils.MyGsonBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmp.rest.models.KGroupe;
import com.qmp.rest.models.KQuestion;
import com.qmp.rest.models.KQuestionnaire;
import com.qmp.rest.models.KRealisation;
import com.qmp.rest.models.KUtilisateur;

public class UserTest {
	private static Gson gson;
	private static String baseUrl;

	@BeforeClass
	public static void setUp() {
		gson = MyGsonBuilder.create();
		baseUrl = "http://127.0.0.1:8080/QMP-Rest/rest/";
		Ko.kstart();
	}

	@Test
	public void testGetAll() {
		try {
			KListObject<KUtilisateur> usersFromDb = KoSession.kloadMany(KUtilisateur.class, "");
			String jsonUsers = HttpUtils.getHTML(baseUrl + "user/all");
			Type listType = new TypeToken<List<KUtilisateur>>() {
			}.getType();
			List<KUtilisateur> users = gson.fromJson(jsonUsers, listType);
			assertEquals(users.size(), usersFromDb.count());
			for (int i = 0; i < users.size(); i++) {
				assertEquals( users.get(i).getNom(), usersFromDb.get(i).getNom());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetGroupUser() {
		try {
			KUtilisateur userFromDb = KoSession.kloadOne(KUtilisateur.class);
			KListObject <KGroupe> groupsUserFromDb = userFromDb.getGroupes();
			String jsonGroupsUser = HttpUtils.getHTML(baseUrl + "user/"+userFromDb.getId()+"/all/groupes");
			Type listType = new TypeToken<List<KGroupe>>() {
			}.getType();
			List<KGroupe> groups = gson.fromJson(jsonGroupsUser, listType);
			assertEquals(groups.size(), groupsUserFromDb.count());
			for (int i = 0; i < groups.size(); i++) {
				assertEquals( groups.get(i).getLibelle(), groupsUserFromDb.get(i).getLibelle());			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Test
	public void testAddOne() {		
		try {
			KListObject <KUtilisateur> user = KoSession.kloadMany(KUtilisateur.class);
			
			HashMap<String, Object> params = new HashMap<>();
			params.put("id", "");
			params.put("mail", "mailTest");
			params.put("password", "passTest");
			params.put("prenom", "nameTest");
			params.put("nom", "nameTest");
			params.put("idRang", "1");
			
			String jsonRep = HttpUtils.putHTML(baseUrl + "user/add/", params);
			
			KListObject <KUtilisateur> userAdded = KoSession.kloadMany(KUtilisateur.class);
			assertNotEquals(user.asAL().size(), userAdded.asAL().size());
			assertEquals(userAdded.get(userAdded.asAL().size()-1).getNom(), "nameTest");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	@Test
	public void testGetRealisations() {
		try {
			KUtilisateur userFromDb = KoSession.kloadOne(KUtilisateur.class, "");
			KListObject <KRealisation> realUserFromDb = userFromDb.getRealisations();
			String jsonRealUser = HttpUtils.getHTML(baseUrl + "user/"+userFromDb.getId()+"/all/realisations");
			Type listType = new TypeToken<List<KRealisation>>() {
			}.getType();
			List<KRealisation> realisations = gson.fromJson(jsonRealUser, listType);
			assertEquals(realisations.size(), realUserFromDb.count());
			for (int i = 0; i < realisations.size(); i++) {
				assertEquals(realisations.get(i).getScore(), realUserFromDb.get(i).getScore());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testUpdateOne() {		
		try {
			KUtilisateur user = KoSession.kloadOne(KUtilisateur.class);
			int idUser = (int) user.getId();
			
			HashMap<String, Object> params = new HashMap<>();
			params.put("id", user.getId());
			params.put("mail", user.getMail()+"-up");
			params.put("password", user.getPassword()+"-up");
			params.put("prenom", user.getPrenom()+"-up");
			params.put("nom", user.getNom()+"-up");
			
			HttpUtils.postHTML(baseUrl + "user/update/"+String.valueOf(idUser), params);
			
			KUtilisateur userUpdated = KoSession.kloadOne(KUtilisateur.class, "id="+idUser);
			assertNotEquals(user.getNom(), userUpdated.getNom());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	

	@Test
	public void testGetOne(){
		try {
			KUtilisateur userFromDb = KoSession.kloadOne(KUtilisateur.class);
			int id = (int) userFromDb.getId();

			String jsonUsers = HttpUtils.getHTML(baseUrl + "user/"+ id);

			KUtilisateur user = gson.fromJson(jsonUsers, KUtilisateur.class);
						
			assertEquals(user.getNom(), userFromDb.getNom());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetQuestions(){
		try {
			KUtilisateur userFromDb = KoSession.kloadOne(KUtilisateur.class, "");
			KListObject<KQuestion> questionsFromDB = userFromDb.getQuestions();
			
			int id = (int) userFromDb.getId();

			String jsonUsers = HttpUtils.getHTML(baseUrl + "user/"+ id+"/all/questions");

			Type listType = new TypeToken<List<KQuestion>>() {
			}.getType();
			List<KQuestion> questions = gson.fromJson(jsonUsers, listType);
			
			assertEquals(questions.size(), questionsFromDB.count());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetQuizzes(){
		try {
			KUtilisateur userFromDb = KoSession.kloadOne(KUtilisateur.class, "");
			KListObject<KQuestionnaire> quizzesFromDB = userFromDb.getQuestionnaires();
			
			int id = (int) userFromDb.getId();

			String jsonUsers = HttpUtils.getHTML(baseUrl + "user/"+ id+"/all/questionnaires");

			Type listType = new TypeToken<List<KQuestion>>() {
			}.getType();
			List<KQuestionnaire> quizzes = gson.fromJson(jsonUsers, listType);
			
			assertEquals(quizzes.size(), quizzesFromDB.count());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDeleteOne(){
		try {
			KUtilisateur user = KoSession.kloadOne(KUtilisateur.class);
			HttpUtils.deleteHTML(baseUrl + "user/"+user.getId());
			KUtilisateur newUser = KoSession.kloadOne(KUtilisateur.class, "id="+user.getId());
			assertEquals(newUser.isLoaded(), false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testConnexion(){
		try {
			KUtilisateur user = KoSession.kloadOne(KUtilisateur.class);
			
			//Connection of a user
			HashMap<String, Object> params = new HashMap<>();
			params.put("login", user.getMail());
			params.put("password",user.getPassword());		
			
			String resultString = HttpUtils.postHTML(baseUrl + "user/connect", params);	
			HashMap<String, String> result = gson.fromJson(resultString, HashMap.class);
			
			//Connection of a false user
			HashMap<String, Object> paramsFalse = new HashMap<>();
			paramsFalse.put("login", user.getMail());
			paramsFalse.put("password", "fauxpassword");
			
			String resultStringFalse = HttpUtils.postHTML(baseUrl + "user/connect", paramsFalse);	
			HashMap<String, String> resultFalse = gson.fromJson(resultStringFalse, HashMap.class);
			
			//Asserting for each user that he is connected or not
			assertEquals(true, result.get("connected"));
			assertNotEquals(true, resultFalse.get("connected"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
