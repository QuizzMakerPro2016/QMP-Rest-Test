package qcm.utils;

import net.ko.kobject.KListObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyGsonBuilder {
	public static Gson create(){
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(KListObject.class, new KlistObjectAdapter());
		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		return gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
	}
}
