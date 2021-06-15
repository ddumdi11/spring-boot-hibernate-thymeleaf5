package de.cmis.test.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.cmis.test.model.CmisBindingEntity;

@Repository
public interface CmisBindingRepository extends CrudRepository<CmisBindingEntity, Long> {

}
