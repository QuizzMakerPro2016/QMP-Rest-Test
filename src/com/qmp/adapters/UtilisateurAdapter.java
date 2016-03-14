package com.qmp.adapters;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.qmp.rest.models.KUtilisateur;

public class UtilisateurAdapter implements JsonSerializer<KUtilisateur>{

	@Override
	public JsonElement serialize(KUtilisateur arg0, Type arg1, JsonSerializationContext arg2) {
		return null;
	}

}
