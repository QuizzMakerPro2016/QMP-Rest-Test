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
	
	public String setValuesToKObject(KObject obj, MultivaluedMap<String, String> formParams){
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
	}
}
