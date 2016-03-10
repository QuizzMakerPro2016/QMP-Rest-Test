package qmp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qmp.adapters.KlistObjectAdapter;

import net.ko.kobject.KListObject;

public class MyGsonBuilder {

	public static Gson create(){
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(KListObject.class, new KlistObjectAdapter());
		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		return gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
	}
}
