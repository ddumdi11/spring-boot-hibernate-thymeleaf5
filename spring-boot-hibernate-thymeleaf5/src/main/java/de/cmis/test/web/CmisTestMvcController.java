package de.cmis.test.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.cmis.test.exceptions.RecordNotFoundException;
import de.cmis.test.model.CmisBindingEntity;
import de.cmis.test.model.CmisUserEntity;
import de.cmis.test.model.TestSettingEntity;
import de.cmis.test.service.CmisBindingService;
import de.cmis.test.service.CmisTestService;
import de.cmis.test.service.CmisUserService;
import de.cmis.test.service.TestSettingService;

@Controller
@RequestMapping("/")
public class CmisTestMvcController {

	@Autowired
	CmisTestService service;

	@Autowired
	CmisUserService userService;

	@Autowired
	CmisBindingService bindingService;

	@Autowired
	TestSettingService settingService;

	@RequestMapping
	public String startPageTests(Model model) throws RecordNotFoundException {

		if (settingService.getActiveSetting() != null) {
			TestSettingEntity activeSetting = settingService.getActiveSetting();
			Long userId = activeSetting.getUserId();
			Long bindingId = activeSetting.getBindingId();
			String userName = userService.getUserById(userId).getUserName();
			String bindingName = bindingService.getBindingById(bindingId).getBindingName();
			
			model.addAttribute("activeSetting", activeSetting);
			model.addAttribute("userName", userName);
			model.addAttribute("bindingName", bindingName);
		}

		return "startpage-tests";
	}

	@RequestMapping(path = { "/listUsers" })
	public String getAllUsers(Model model) {
		List<CmisUserEntity> list = userService.getAllUsers();

		model.addAttribute("users", list);
		return "list-users";
	}

	@RequestMapping(path = { "/listBindings" })
	public String getAllBindings(Model model) {
		List<CmisBindingEntity> list = bindingService.getAllBindings();

		model.addAttribute("bindings", list);
		return "list-bindings";
	}

	@RequestMapping(path = "/setTestSetting", method = RequestMethod.GET)
	public String setActiveSetting(Model model) throws RecordNotFoundException {
		List<CmisUserEntity> listUsers = userService.getAllUsers();
		List<CmisBindingEntity> listBindings = bindingService.getAllBindings();
		TestSettingEntity setting = new TestSettingEntity();

		model.addAttribute("users", listUsers);
		model.addAttribute("bindings", listBindings);
		model.addAttribute("setting", setting);

		return "set-test-setting";
	}

	@RequestMapping(path = "/addNewSetting", method = RequestMethod.POST)
	public String createSetting(@ModelAttribute("setting") TestSettingEntity newSetting)
			throws RecordNotFoundException {
		settingService.createSetting(newSetting);
		return "redirect:/";
	}

	@RequestMapping(path = { "/editUser", "/editUser/{id}" })
	public String editUserById(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
		if (id.isPresent()) {
			CmisUserEntity entity = userService.getUserById(id.get());
			model.addAttribute("user", entity);
		} else {
			model.addAttribute("user", new CmisUserEntity());
		}
		return "add-edit-user";
	}

	@RequestMapping(path = "/addNewUser", method = RequestMethod.POST)
	public String createOrUpdateUser(CmisUserEntity newUser) {
		userService.createOrUpdateUser(newUser);
		return "redirect:/";
	}

	@RequestMapping(path = "/deleteUser/{id}")
	public String deleteUserById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
		userService.deleteUserById(id);
		return "redirect:/";
	}

	@RequestMapping(path = { "/editBinding", "/editBinding/{id}" })
	public String editBindingById(Model model, @PathVariable("id") Optional<Long> id) throws RecordNotFoundException {
		if (id.isPresent()) {
			CmisBindingEntity entity = bindingService.getBindingById(id.get());
			model.addAttribute("binding", entity);
		} else {
			model.addAttribute("binding", new CmisBindingEntity());
		}
		return "add-edit-binding";
	}

	@RequestMapping(path = "/addNewBinding", method = RequestMethod.POST)
	public String createOrUpdateBinding(CmisBindingEntity newBinding) {
		bindingService.createOrUpdateBinding(newBinding);
		return "redirect:/";
	}

	@RequestMapping(path = "/deleteBinding/{id}")
	public String deleteBindingById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
		bindingService.deleteBindingById(id);
		return "redirect:/";
	}

	@RequestMapping(path = { "/startAdminSession" })
	public String startAdminSession(Model model) {
		service.startAdminSession();
		return "redirect:/";
	}

	@RequestMapping(path = { "/startUserSession" })
	public String startUserSession(Model model) throws RecordNotFoundException {
		service.startUserSession();
		return "redirect:/";
	}

	@RequestMapping(path = { "/listTopFolder" })
	public String listTopFolder(Model model) {
		service.listTopFolder();
		return "redirect:/";
	}

	@RequestMapping(path = { "/getListTopFolder" })
	public String getListTopFolder(Model model) {
		List<String> topFolderList = service.getListTopFolder();
		model.addAttribute("topFolderList", topFolderList);
		return "list-top-folder";
	}

	@RequestMapping(path = { "/createTestFolder" })
	public String createTestFolder(Model model) {
		service.createTestFolder();
		return "redirect:/";
	}

	@RequestMapping(path = { "/deleteTestFolder" })
	public String deleteTestFolder(Model model) {
		service.deleteTestFolder();
		return "redirect:/";
	}
	
	@RequestMapping(path = { "/entryPoint" })
	public String entryPoint(Model model) throws RecordNotFoundException {
		service.testEntryPoint();
		return "redirect:/";
	}
	
	@RequestMapping(path = { "/getNodesRootFolder" })
	public String getNodesRootFolder(Model model) throws RecordNotFoundException {
		service.getNodesRootFolder();
		return "redirect:/";
	}

}
