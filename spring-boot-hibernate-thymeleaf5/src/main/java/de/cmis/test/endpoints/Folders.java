package de.cmis.test.endpoints;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;

import de.cmis.test.sessions.AdminSessionSingleton;

public class Folders {

	public static Folder createFolder() {
		Session session = AdminSessionSingleton.getInstance().getAdminSession();
		String folderName = "OpenCMISTest";
		Folder parentFolder = session.getRootFolder();

		// Make sure the user is allowed to create a folder
		// under the root folder
		if (parentFolder.getAllowableActions().getAllowableActions().contains(Action.CAN_CREATE_FOLDER) == false) {
			throw new CmisUnauthorizedException(
					"Current user does not have permission to create a " + "sub-folder in " + parentFolder.getPath());
		}

		// Check if folder already exist, if not create it
		Folder newFolder = (Folder) getObject(session, parentFolder, folderName);
		if (newFolder == null) {
			Map<String, Object> newFolderProps = new HashMap<String, Object>();
			newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
			newFolderProps.put(PropertyIds.NAME, folderName);
			newFolder = parentFolder.createFolder(newFolderProps);
			System.out.println("Created new folder: " + newFolder.getPath() + " [creator=" + newFolder.getCreatedBy()
					+ "][created=" + date2String(newFolder.getCreationDate().getTime()) + "]");
		} else {
			System.out.println("Folder already exist: " + newFolder.getPath());
		}

		return newFolder;
	}

	public static void deleteFolder() {
		Session session = AdminSessionSingleton.getInstance().getAdminSession();
		String folderName = "OpenCMISTest";
		String queryFolderByName = "cmis:name LIKE '" + folderName + "'";
		OperationContext oc = session.createOperationContext();
		//oc.setFilterString("cmis:object");
		ItemIterable<CmisObject> results = session.queryObjects("cmis:folder", queryFolderByName, false, oc);
		// true = alle Versionen finden (erst ab CMIS 1.1 supported!)

		for (CmisObject cmisObject : results) {
		    Folder folder = (Folder) cmisObject; // it can only be a folder
		    System.out.println(folder.getName() + " wird jetzt gel??scht!");
		    try {
				folder.delete(true); // (true = alle Versionen l??schen, false = nicht alle Versionen l??schen)
				System.out.println("Ordner ist gel??scht!");
			} catch (Exception e) {
				System.out.println("Ordner l??sst sich nicht l??schen!");
			}		    
		}		

	}

	private static CmisObject getObject(Session session, Folder parentFolder, String objectName) {
		CmisObject object = null;
		try {
			String path2Object = parentFolder.getPath();
			if (!path2Object.endsWith("/")) {
				path2Object += "/";
			}
			path2Object += objectName;
			object = session.getObjectByPath(path2Object);
		} catch (CmisObjectNotFoundException nfe0) {
			// Nothing to do, object does not exist
		}

		return object;
	}

	private static String date2String(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(date);
	}

}
