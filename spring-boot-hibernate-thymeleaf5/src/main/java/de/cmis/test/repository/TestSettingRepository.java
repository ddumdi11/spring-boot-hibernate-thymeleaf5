package de.cmis.test.repository;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.cmis.test.model.TestSettingEntity;

@Repository
public interface TestSettingRepository extends CrudRepository<TestSettingEntity, Long> {
	


}
