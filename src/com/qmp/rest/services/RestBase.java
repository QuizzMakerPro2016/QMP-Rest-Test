package com.qmp.rest.services;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qmp.adapters.KlistObjectAdapter;

import net.ko.framework.KoHttp;
import net.ko.kobject.KListObject;
import net.ko.kobject.KObject;

public class RestBase {
	
	@Context
	protected ServletContext context;
	protected Gson gson;
	
	public RestBase() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(KListObject.class, new KlistObjectAdapter());
		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
	}

	@Context
	public void setServletContext(ServletContext context) {
		this.context = context;
		KoHttp.kstart(context);
	}
	
	/*public String setValuesToKObject(KObject obj, MultivaluedMap<String, String> formParams){
		String msg = null;
		
		for (String param : formParams.keySet()) {
			try {
				String value = formParams.get(param) + "";
				value = value.replaceFirst("^\\[(.*)\\]$", "$1");
				obj.setAttribute(param, value, false);
			} catch (SecurityException | IllegalArgumentException
					| NoSuchFieldException | IllegalAccessException
					| InvocationTargetException e) {
				msg = "{\"message\": \" "+e.toString()+" }";

			}
		}
		
		return msg;
	}*/
	
	/**
	 * retourne un message JSON contenant l'objet affect�
	 * 
	 * @param message message de retour
	 * @param key cl� de l'objet affect�
	 * @param value objet affect�
	 * @param keyValues fin de cha�ne JSON � ajouter � la r�ponse
	 * @return
	 */
	protected <T> String returnValue(String message, String key, T value, String keyValues) {
		String jsonEnd = "";
		if (!"".equals(keyValues))
			jsonEnd = "," + keyValues;
		return "{\"message\":\"" + message + "\",\"" + key + "\":" + gson.toJson(value) + jsonEnd + "}";
	}

	/**
	 * retourne une cha�ne JSON contenant un message, l'objet affect� et sa cl�
	 * 
	 * @param message
	 * @param key
	 * @param value
	 * @return
	 */
	protected <T> String returnValue(String message, String key, T value) {
		return returnValue(message, key, value, "");
	}

	/**
	 * retourne une cha�ne JSON contenant un message et l'objet affect�
	 * 
	 * @param message
	 * @param value
	 * @return
	 */
	protected <T> String returnValue(String message, T value) {
		return returnValue(message, "object", value);
	}

	/**
	 * retourne une cha�ne JSON contenant un message
	 * 
	 * @param message
	 * @return
	 */
	protected String returnMessage(String message) {
		return returnMessage(message, false);
	}

	/**
	 * retourne une cha�ne JSON contenant un message avec erreur ou non
	 * 
	 * @param message contenu du message
	 * @param hasError pr�sence d'une erreur
	 * @return
	 */
	protected String returnMessage(String message, boolean hasError) {
		return "{\"message\":\"" + message + "\",\"error\":" + hasError + "}";
	}
}
