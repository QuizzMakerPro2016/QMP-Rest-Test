package qcm.utils;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import net.ko.kobject.KListObject;
import net.ko.kobject.KObject;

public class KlistObjectAdapter
		implements JsonSerializer<KListObject<? extends KObject>>,
		JsonDeserializer<KListObject<? extends KObject>> {

	@Override
	public JsonElement serialize(KListObject<? extends KObject> list, Type arg1, JsonSerializationContext context) {
		JsonElement elm = context.serialize(list.asAL());
		return elm;
	}

	@Override
	public KListObject<? extends KObject> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
		Type listType = new TypeToken<List<? extends KObject>>() {
		}.getType();
		List<? extends KObject> l = context.deserialize(json.getAsJsonArray(), listType);
		KListObject<? extends KObject> kl = new KListObject<>(KObject.class);
		for (KObject o : l) {
			kl.add(o, true);
		}
		return kl;
	}

}
