package qmp.rest.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.google.gson.Gson;

import qmp.utils.HttpUtils;
import qmp.utils.MyGsonBuilder;

public class UserTest {
	private Gson gson;
	private String baseURL;
	
	public void setUp(){
		gson = MyGsonBuilder.create();
		baseURL = "http://localhost:8080/QMP-Rest/rest";
	}

	@Test
	public void testAll() {
		try {
			String jsonUsers = HttpUtils.getHTML(baseURL + "user/all");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testAddGroup() {
		fail("Not yet implemented");
	}

}
