package de.cmis.test.categories;

import java.util.Map;

import org.apache.chemistry.opencmis.client.api.ObjectType;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.definitions.PropertyDefinition;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;

public class CmisEGovHandbuch {
	
	private static final String nscale_GOV_File = "nscale:GOV_FILE";
	private static final String nscale_gov_subject = "nscale:gov_subject";

	public static boolean checkIfTypeExists(Session session, String myType) {
		// überprüfen ob es eine bestimmte Typdefinition gibt
		boolean typeExists = true;
		try {
			session.getTypeDefinition(myType);
		} catch (CmisObjectNotFoundException exc) {
			typeExists = false;
		}

		return typeExists;
	}

	public static String getPropertyDescription(Session session, String myFile, String mySubject) {

		// Informationen zu einem Metadatum aus den Typdefinitionen ermitteln
		ObjectType type = session.getTypeDefinition("nscale:GOV_FILE ");
		Map<String, PropertyDefinition<?>> propertydefinitions = type.getPropertyDefinitions();
		PropertyDefinition<?> property = propertydefinitions.get("nscale:gov_subject");
		String propertyBeschreibung = property.getDescription();

		return propertyBeschreibung;
	}
	
	

}
