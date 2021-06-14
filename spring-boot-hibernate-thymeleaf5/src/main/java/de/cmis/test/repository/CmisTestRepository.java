package de.cmis.test.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.cmis.test.model.CmisTestEntity;

@Repository
public interface CmisTestRepository extends CrudRepository<CmisTestEntity, Long> {

}
