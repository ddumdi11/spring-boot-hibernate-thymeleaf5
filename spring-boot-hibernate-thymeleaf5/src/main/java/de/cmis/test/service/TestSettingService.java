package de.cmis.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.cmis.test.exceptions.RecordNotFoundException;
import de.cmis.test.model.TestSettingEntity;
import de.cmis.test.repository.TestSettingRepository;

@Service
public class TestSettingService {

	@Autowired
	TestSettingRepository settingRepo;

	public TestSettingEntity getActiveSetting() throws RecordNotFoundException {
		TestSettingEntity activeSetting = new TestSettingEntity();
		List<TestSettingEntity> results = (List<TestSettingEntity>) settingRepo.findAll();

		if (results.size() > 0) {
			for (TestSettingEntity iter : results) {
				if (iter.getIsActive() == true) {
					activeSetting = iter;
				}
			}
		} else {
			activeSetting = null;
		}		
		
		return activeSetting;
	}

	public void createSetting(TestSettingEntity entity) throws RecordNotFoundException {
		TestSettingEntity newEntity = new TestSettingEntity();
		newEntity.setUserId(entity.getUserId());
		newEntity.setBindingId(entity.getBindingId());
		newEntity.setIsActive(true);
		newEntity.setSettingDate();

		for (TestSettingEntity instance : settingRepo.findAll()) {
			if (instance.getIsActive() == true) {
				instance.setIsActive(false);
			}
		};
		
		newEntity = settingRepo.save(newEntity);

	}

}
