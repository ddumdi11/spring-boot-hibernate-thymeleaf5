package de.cmis.test.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.cmis.test.exceptions.RecordNotFoundException;
import de.cmis.test.model.CmisUserEntity;
import de.cmis.test.service.CmisTestService;
import de.cmis.test.service.CmisUserService;

@Controller
@RequestMapping("/")
public class CmisTestMvcController {

	@Autowired
	CmisTestService service;
	
	@Autowired
	CmisUserService userService;

	@RequestMapping
	public String startPageTests(Model model) {
		return "startpage-tests";
	}
	
	@RequestMapping(path = {"/changeUserSettings"})
	public String getAllUsers(Model model) 
	{
		List<CmisUserEntity> list = userService.getAllUsers();

		model.addAttribute("users", list);
		return "list-users";
	}
	
	@RequestMapping(path = {"/edit", "/edit/{id}"})
	public String editUserById(Model model, @PathVariable("id") Optional<Long> id) 
							throws RecordNotFoundException 
	{
		if (id.isPresent()) {
			CmisUserEntity entity = userService.getUserById(id.get());
			model.addAttribute("user", entity);
		} else {
			model.addAttribute("user", new CmisUserEntity());
		}
		return "add-edit-user";
	}
	
	@RequestMapping(path = "/delete/{id}")
	public String deleteUserById(Model model, @PathVariable("id") Long id) 
							throws RecordNotFoundException 
	{
		userService.deleteUserById(id);
		return "redirect:/";
	}

	@RequestMapping(path = "/createUser", method = RequestMethod.POST)
	public String createOrUpdateEmployee(CmisUserEntity user) 
	{
		userService.createOrUpdateUser(user);
		return "redirect:/";
	}

	@RequestMapping(path = { "/startAdminSession" })
	public String startAdminSession(Model model) {
		service.startAdminSession();
		return "redirect:/";
	}

	@RequestMapping(path = { "/startUserSession" })
	public String startUserSession(Model model, String connectionName, String username, String pwd) {
		service.startUserSession(connectionName, username, pwd);
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

}
