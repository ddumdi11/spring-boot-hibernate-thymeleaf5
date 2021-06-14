package de.cmis.test.sessions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Repository;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.exceptions.CmisConnectionException;

import de.cmis.test.Constants.AlfrescoConstants;

public class UserSessionSingleton {

	private static UserSessionSingleton instance; // vor Zugriff von außen geschützt und statisch

	private UserSessionSingleton() {
	} // privater Konstruktor mit Zugriffsschutz von außen

	public static UserSessionSingleton getInstance() { // öffentliche Methode, Aufruf durch Code
		if (instance == null) { // nur wenn keine Instanz besteht, dann erstelle eine neue
			instance = new UserSessionSingleton();
		}
		return instance;
	}

	public Session getUserSession(String connectionName, String username, String pwd) {
		Session session = null;

		// No connection to Alfresco available, create a new one
		SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(SessionParameter.USER, username);
		parameters.put(SessionParameter.PASSWORD, pwd);
		parameters.put(SessionParameter.ATOMPUB_URL,
				"http://localhost:8080/alfresco/api/-default-/cmis/versions/1.1/atom");
		parameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
		parameters.put(SessionParameter.COMPRESSION, "true");
		parameters.put(SessionParameter.CACHE_TTL_OBJECTS, "0");

		// If there is only one repository exposed (e.g. Alfresco),
		// these lines will help detect it and its ID
		List<Repository> repositories = sessionFactory.getRepositories(parameters);
		Repository alfrescoRepository = null;
		if (repositories != null && repositories.size() > 0) {
			System.out.println("Found (" + repositories.size() + ") Alfresco repositories");
			alfrescoRepository = repositories.get(0);
			System.out.println("Info about the first Alfresco repo [ID=" + alfrescoRepository.getId() + "][name="
					+ alfrescoRepository.getName() + "][CMIS ver supported="
					+ alfrescoRepository.getCmisVersionSupported() + "]");
		} else {
			throw new CmisConnectionException("Could not connect to the Alfresco Server, " + "no repository found!");
		}

		// Create a new session with the Alfresco repository
		session = alfrescoRepository.createSession();

		// Save connection for reuse
		// connections.put(connectionName, session);

		// Rückmeldung, dass die Session erzeugt wurde
		System.out.println("Die Session wurde erzeugt!");

		// Rückgabe
		return session;
	}

}
