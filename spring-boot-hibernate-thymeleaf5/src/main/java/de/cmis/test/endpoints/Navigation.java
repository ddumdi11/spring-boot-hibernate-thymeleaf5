package de.cmis.test.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.ContentStream;

import de.cmis.test.sessions.AdminSessionSingleton;

public class Navigation {
	
	public void listTopFolder() {
		Session session = AdminSessionSingleton.getInstance().getAdminSession();
		Folder root = session.getRootFolder();
		ItemIterable<CmisObject> contentItems= root.getChildren();
		for (CmisObject contentItem : contentItems) {
			if (contentItem instanceof Document) {
				Document docMetadata = (Document)contentItem;
				ContentStream docContent = docMetadata.getContentStream();
					
				System.out.println(docMetadata.getName() + " [size=" +
						docContent.getLength()+"][Mimetype=" +
						docContent.getMimeType()+"][type=" +
						docMetadata.getType().getDisplayName()+"]");
			} else {
				System.out.println(contentItem.getName() + "[type="+contentItem.getType().getDisplayName()+"]");
			}
		}
	}
	
	public List<String> getListTopFolder() {
		List<String> topFolderList = new ArrayList<>();
		Session session = AdminSessionSingleton.getInstance().getAdminSession();
		Folder root = session.getRootFolder();
		ItemIterable<CmisObject> contentItems= root.getChildren();
		for (CmisObject contentItem : contentItems) {
			if (contentItem instanceof Document) {
				Document docMetadata = (Document)contentItem;
				ContentStream docContent = docMetadata.getContentStream();
					
				topFolderList.add(docMetadata.getName() + " [size=" +
						docContent.getLength()+"][Mimetype=" +
						docContent.getMimeType()+"][type=" +
						docMetadata.getType().getDisplayName()+"]");				
			} else {
				topFolderList.add(contentItem.getName() + "[type="+contentItem.getType().getDisplayName()+"]");				
			}
		}
		return topFolderList;
	}


}
