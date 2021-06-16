package de.cmis.test.service;


import java.util.List;


import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.cmis.test.categories.CurlTests;
import de.cmis.test.endpoints.Folders;
import de.cmis.test.endpoints.Navigation;
import de.cmis.test.exceptions.RecordNotFoundException;
import de.cmis.test.model.CmisTestEntity;
import de.cmis.test.model.TestSettingEntity;
import de.cmis.test.repository.CmisTestRepository;
import de.cmis.test.sessions.AdminSessionSingleton;
import de.cmis.test.sessions.UserSessionSingleton;

@Service
public class CmisTestService {

	@Autowired
	CmisTestRepository testRepo;
	
	@Autowired
	TestSettingService settingService;
	
	@Autowired
	CmisUserService userService;
	
	@Autowired
	CmisBindingService bindingService;
	
	public void startAdminSession() {
		CmisTestEntity testEntity = new CmisTestEntity();
		testEntity.setTestName("Start Admin Session Atom Cmis 1.1");
		testEntity.setTestKategorie("Admin Session Erzeugung Singleton + Timeout");
		testEntity.setTestDatum();
		try {
			Session sessionAdmin = AdminSessionSingleton.getInstance().getAdminSession();
			testEntity.setTestErgebnis("positiv");
		} catch (Exception e) {
			testEntity.setTestErgebnis("negativ");
			System.err.println(e);
		}
		testRepo.save(testEntity);
	}
		
	public String[] activeSettingParams () throws RecordNotFoundException {
		TestSettingEntity setting = settingService.getActiveSetting();
		String userName = userService.getUserById(setting.getUserId()).getUserName();
		String userPwd = userService.getUserById(setting.getUserId()).getUserPwd();
		String bindingName = bindingService.getBindingById(setting.getBindingId()).getBindingName();
		String bindingUrl = bindingService.getBindingById(setting.getBindingId()).getBindingUrl();
		String[] params = {userName,userPwd,bindingName,bindingUrl};
		
		return params;
	}
	
	public void startUserSession() throws RecordNotFoundException {
		CmisTestEntity testEntity = new CmisTestEntity();
		String[] params = activeSettingParams();
		String userName = params[0];
		String userPwd = params[1];
		String bindingName = params[2];
		String bindingUrl = params[3];
		testEntity.setTestName("Start User '" + userName + "' Session " + bindingName);
		testEntity.setTestKategorie("User Session Erzeugung Singleton + Timeout");
		testEntity.setTestDatum();
		System.out.println("StartUserSession im CmisTestService: " + userName + " & " + bindingName);
		try {
			Session sessionUser = UserSessionSingleton.getInstance().getUserSession(userName, userPwd, bindingUrl);
			testEntity.setTestErgebnis("positiv");
		} catch (Exception e) {
			testEntity.setTestErgebnis("negativ");
			System.err.println(e);
		}
		testRepo.save(testEntity);
	}
	

	public void listTopFolder() {
		CmisTestEntity testEntity = new CmisTestEntity();
		testEntity.setTestName("Admin List Top Folder");
		testEntity.setTestKategorie("Navigation Admin");
		testEntity.setTestDatum();
		try {
			Navigation navigation = new Navigation();
			navigation.listTopFolder();
			testEntity.setTestErgebnis("positiv");
		} catch (Exception e) {
			testEntity.setTestErgebnis("negativ");
			System.err.println(e);
		}
		testRepo.save(testEntity);
	}
	
	public List<String> getListTopFolder() {
		CmisTestEntity testEntity = new CmisTestEntity();
		testEntity.setTestName("Admin List Top Folder");
		testEntity.setTestKategorie("Navigation Admin");
		testEntity.setTestDatum();
		List<String> topFolderList = null;
		try {
			topFolderList = Navigation.getListTopFolder();
			testEntity.setTestErgebnis("positiv");
		} catch (Exception e) {
			testEntity.setTestErgebnis("negativ");
			System.err.println(e);
		}
		testRepo.save(testEntity);
		return topFolderList;
	}
	
	public void createTestFolder() {
		CmisTestEntity testEntity = new CmisTestEntity();
		testEntity.setTestName("Create Test-Folder");
		testEntity.setTestKategorie("Objects / Folder");
		testEntity.setTestDatum();
		try {
			Folder folder = Folders.createFolder();
			System.out.print("Neu erzeugter Ordner: " + folder.getName());
			testEntity.setTestErgebnis("positiv");
		} catch (Exception e) {
			testEntity.setTestErgebnis("negativ");
			System.err.println(e);
		}
		testRepo.save(testEntity);
	}
	
	public void deleteTestFolder() {
		CmisTestEntity testEntity = new CmisTestEntity();
		testEntity.setTestName("Delete Test-Folder");
		testEntity.setTestKategorie("Objects / Folder");
		testEntity.setTestDatum();
		try {
			Folders.deleteFolder();
			testEntity.setTestErgebnis("positiv");
		} catch (Exception e) {
			testEntity.setTestErgebnis("negativ");
			System.err.println(e);
		}
		testRepo.save(testEntity);
	}
	
	public void testEntryPoint() throws RecordNotFoundException {
		String[] params = activeSettingParams();
		CmisTestEntity testEntity = new CmisTestEntity();
		testEntity.setTestName("Entry Point Atom");
		testEntity.setTestKategorie("Curl Tests");
		testEntity.setTestDatum();
		try {
			CurlTests.entryPoint(params);
			testEntity.setTestErgebnis("positiv");
		} catch (Exception e) {
			testEntity.setTestErgebnis("negativ");
			System.err.println(e);
		}
		testRepo.save(testEntity);
	}
	
	public void getNodesRootFolder() throws RecordNotFoundException {
		String[] params = activeSettingParams();
		CmisTestEntity testEntity = new CmisTestEntity();
		testEntity.setTestName("Nodes Root-Folder");
		testEntity.setTestKategorie("Curl Tests");
		testEntity.setTestDatum();
		try {			
			CurlTests.getNodesRootFolder(params);
			testEntity.setTestErgebnis("positiv");
		} catch (Exception e) {
			testEntity.setTestErgebnis("negativ");
			System.err.println(e);
		}
		testRepo.save(testEntity);
	}

}
