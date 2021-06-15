package de.cmis.test.service;


import java.util.List;


import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	@Autowired
	TestSettingService settingService;
	
	@Autowired
	CmisUserService userService;
	
	@Autowired
	CmisBindingService bindingService;
	
	public void startUserSession() throws RecordNotFoundException {
		CmisTestEntity testEntity = new CmisTestEntity();
		TestSettingEntity setting = settingService.getActiveSetting();
		String userName = userService.getUserById(setting.getUserId()).getUserName();
		String userPwd = userService.getUserById(setting.getUserId()).getUserPwd();
		String bindingName = bindingService.getBindingById(setting.getBindingId()).getBindingName();
		String bindingUrl = bindingService.getBindingById(setting.getBindingId()).getBindingUrl();
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
			Navigation navigation = new Navigation();
			topFolderList = navigation.getListTopFolder();
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
			Folders folders = new Folders();
			Folder folder = folders.createFolder();
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
			Folders folders = new Folders();
			folders.deleteFolder();
			testEntity.setTestErgebnis("positiv");
		} catch (Exception e) {
			testEntity.setTestErgebnis("negativ");
			System.err.println(e);
		}
		testRepo.save(testEntity);
	}

}
