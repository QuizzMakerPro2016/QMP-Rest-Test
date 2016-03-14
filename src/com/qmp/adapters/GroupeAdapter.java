package com.qmp.adapters;
import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.qmp.rest.models.KGroupe;


public class GroupeAdapter implements JsonSerializer<KGroupe>{

	@Override
	public JsonElement serialize(KGroupe groupe, Type arg1, JsonSerializationContext context) {
		JsonObject jsonObject=new JsonObject();
		jsonObject.addProperty("libelle", groupe.getLibelle());
		return jsonObject;
	}

}
