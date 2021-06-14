package de.cmis.test.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.cmis.test.model.CmisUserEntity;

@Repository
public interface CmisUserRepository extends CrudRepository<CmisUserEntity, Long> {

}
