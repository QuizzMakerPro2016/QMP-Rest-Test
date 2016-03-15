package quiz.rest.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.PathParam;

import net.ko.framework.Ko;
import net.ko.framework.KoSession;
import net.ko.kobject.KListObject;
import net.ko.kobject.KSession;

import org.junit.BeforeClass;
import org.junit.Test;

import qcm.utils.HttpUtils;
import qcm.utils.MyGsonBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.qmp.rest.models.KGroupe;
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
			KListObject<KUtilisateur> usersFromDb = KoSession.kloadMany(KUtilisateur.class);
			String jsonUsers = HttpUtils.getHTML(baseUrl + "user/all");
			Type listType = new TypeToken<List<KUtilisateur>>() {
			}.getType();
			List<KUtilisateur> users = gson.fromJson(jsonUsers, listType);
			assertEquals(users.size(), usersFromDb.count());
			for (int i = 0; i < users.size(); i++) {
				assertEquals( users.get(i).getNom(), usersFromDb.get(i).getNom());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetGroupUser() {
		try {
			KUtilisateur userFromDb = KoSession.kloadOne(KUtilisateur.class, 4);
			KListObject <KGroupe> groupsUserFromDb = userFromDb.getGroupes();
			String jsonGroupsUser = HttpUtils.getHTML(baseUrl + "user/4/all/groupes");
			Type listType = new TypeToken<List<KGroupe>>() {
			}.getType();
			List<KGroupe> groups = gson.fromJson(jsonGroupsUser, listType);
			assertEquals(groups.size(), groupsUserFromDb.count());
			for (int i = 0; i < groups.size(); i++) {
				assertEquals( groups.get(i).getLibelle(), groupsUserFromDb.get(i).getLibelle());			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
			KUtilisateur user = KoSession.kloadOne(KUtilisateur.class, "");
			int idUser = (int) user.getId();
			
			HashMap<String, Object> params = new HashMap<>();
			params.put("id", user.getId());
			params.put("mail", user.getMail()+"-up");
			params.put("password", user.getPassword()+"-up");
			params.put("prenom", user.getPrenom()+"-up");
			params.put("nom", user.getNom()+"-up");
			
			String jsonRep = HttpUtils.postHTML(baseUrl + "user/update/"+String.valueOf(idUser), params);
			
			KUtilisateur userUpdated = KoSession.kloadOne(KUtilisateur.class, "id="+idUser);
			assertNotEquals(user.getNom(), userUpdated.getNom());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	@Test
	public void testGetOne(){
		try {
			KUtilisateur userFromDb = KoSession.kloadOne(KUtilisateur.class, "");
			String jsonUsers = HttpUtils.getHTML(baseUrl + "user/"+ userFromDb.getId());
			Type listType = new TypeToken<List<KUtilisateur>>() {
			}.getType();
			KUtilisateur user = gson.fromJson(jsonUsers, listType);
			
			assertEquals(user.getNom(), userFromDb.getNom());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
