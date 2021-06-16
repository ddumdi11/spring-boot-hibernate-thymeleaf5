package de.cmis.test.formatting;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

// https://stackoverflow.com/questions/4105795/pretty-print-json-in-java

public class PrettyJson {

	public static String prettyPrint(String jsonString) throws Exception {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonElement je = JsonParser.parseString(jsonString);

		return gson.toJson(je);
	}

}