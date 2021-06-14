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

	public Folder createFolder() {
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

	public void deleteFolder() {
		Session session = AdminSessionSingleton.getInstance().getAdminSession();
		String folderName = "OpenCMISTest";
		String folderPath = "/";
		String queryFolderByName = "cmis:name LIKE '" + folderPath + folderName + "'";
		OperationContext oc = session.createOperationContext();
		oc.setFilterString("cmis:objectId,cmis:folder,cmis:name,cmis:createdBy");
		ItemIterable<CmisObject> results = session.queryObjects("cmis:folder",queryFolderByName, false, oc);

		for (CmisObject cmisObject : results) {
		    Folder folder = (Folder) cmisObject; // it can only be a folder
		    System.out.println(folder.getName() + " wird jetzt gelöscht!");
		    try {
				folder.delete(false); // Nicht alle Versionen löschen (true = alle Versionen löschen)
				System.out.println("Ordner ist gelöscht!");
			} catch (Exception e) {
				System.out.println("Ordner lässt sich nicht löschen!");
			}		    
		}		

	}

	private CmisObject getObject(Session session, Folder parentFolder, String objectName) {
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

	private String date2String(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(date);
	}

}
