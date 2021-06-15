package de.cmis.test.curl;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.chemistry.opencmis.commons.impl.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import de.cmis.test.exceptions.RecordNotFoundException;
import de.cmis.test.model.TestSettingEntity;
import de.cmis.test.service.CmisBindingService;
import de.cmis.test.service.CmisUserService;
import de.cmis.test.service.TestSettingService;

public class CurlTest {

	public void processCurl1(String[] params) throws IOException, RecordNotFoundException {
		String bindingUrl = params[3];
		String command = "curl -X GET " + bindingUrl;
		System.out.println(command);
		Process process = Runtime.getRuntime().exec(command);
		InputStream inputStream = process.getInputStream();
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);

		// JSONObject jsonObject = new JSONObject();

		System.out.println(responseStrBuilder.toString());

		process.destroy();
	}

}
