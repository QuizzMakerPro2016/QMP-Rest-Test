package com.qmp.adapters;

import java.lang.reflect.Type;

import net.ko.kobject.KListObject;
import net.ko.kobject.KObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class KlistObjectAdapter implements JsonSerializer<KListObject<? extends KObject>> {

	@Override
	public JsonElement serialize(KListObject<? extends KObject> list, Type arg1, JsonSerializationContext context) {
		JsonElement elm = context.serialize(list.asAL());
		return elm;
	}

}