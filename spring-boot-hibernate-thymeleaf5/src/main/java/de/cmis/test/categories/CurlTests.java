package de.cmis.test.categories;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.cmis.test.formatting.PrettyJson;
import de.cmis.test.formatting.PrettyXml;

public class CurlTests {

	public static void entryPoint(String[] params) throws Exception {
		String userName = params[0];
		String userPwd = params[1];
		String bindingUrl = params[3];
		String commandAuth = "curl -X GET " + bindingUrl + " -u " + userName + ":" + userPwd;
		String commandNoAuth = "curl -X GET " + bindingUrl;

		// Mit Authentifizierung
		System.out.println("EntryPoint-Avfrage mit Authentifizierung:\n" + commandAuth);
		// Erwartet wird Atom-XML Format
		String outAtom = getOutputFromCurlCmd(commandAuth);
		System.out.println(PrettyXml.prettyPrint(outAtom));

		// Ohne Authentifizierung
		System.out.println("EntryPoint-Abfrage ohne Authentifizierung:\n" + commandNoAuth);
		// Erwartet wird JSON Format
		String outJson = getOutputFromCurlCmd(commandNoAuth);
		System.out.println(PrettyJson.prettyPrint(outJson));

	}

	public static String getOutputFromCurlCmd(String commandCurl) throws IOException {
		Process process = Runtime.getRuntime().exec(commandCurl);
		InputStream inputStream = process.getInputStream();
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);

		String output = responseStrBuilder.toString();

		process.destroy();

		return output;
	}

	public static String createTestFolder(String[] params) throws IOException {
		String userName = params[0];
		String userPwd = params[1];
		String bindingUrl = params[3];
		String commandAuth = "curl -X GET " + bindingUrl + " -u " + userName + ":" + userPwd;
		String commandNoAuth = "curl -X GET " + bindingUrl;

		StringBuilder responseCreateTestFolder = new StringBuilder();

		responseCreateTestFolder.append(getOutputFromCurlCmd(commandAuth));
		responseCreateTestFolder.append(getOutputFromCurlCmd(commandNoAuth));

		return responseCreateTestFolder.toString();
	}

	public static void getNodesRootFolder(String[] params) throws Exception {
		String userName = params[0];
		String userPwd = params[1];
		String bindingUrl = params[3];
		String commandNoAuth = "curl -X GET -H 'Accept: application/json' http://localhost:8080/alfresco/api/-default-/public/alfresco/versions/1/nodes/-root-/children";
		String commandAuth = commandNoAuth + " -u " + userName + ":" + userPwd;

		// Mit Authentifizierung
		System.out.println("Nodes des Rootfolders Abfrage mit Authentifizierung:\n" + commandAuth);
		// Erwartet wird JSON Format
		String outAtom = getOutputFromCurlCmd(commandAuth);
		System.out.println("Json:\n" + outAtom);
		System.out.println(PrettyJson.prettyPrint(outAtom));

		// Ohne Authentifizierung
		System.out.println("Nodes des Rootfolders Abfrage ohne Authentifizierung:\n" + commandNoAuth);
		// Erwartet wird JSON Format
		String outJson = getOutputFromCurlCmd(commandNoAuth);
		System.out.println(PrettyJson.prettyPrint(outJson));

	}

}
