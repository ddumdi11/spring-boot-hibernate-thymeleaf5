package de.cmis.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.cmis.test.exceptions.RecordNotFoundException;
import de.cmis.test.model.CmisUserEntity;
import de.cmis.test.repository.CmisUserRepository;

@Service
public class CmisUserService {

	@Autowired
	CmisUserRepository userRepo;

	public List<CmisUserEntity> getAllUsers() {
		List<CmisUserEntity> result = (List<CmisUserEntity>) userRepo.findAll();

		if (result.size() > 0) {
			return result;
		} else {
			return new ArrayList<CmisUserEntity>();
		}
	}
	
	public CmisUserEntity getUserById(Long id) throws RecordNotFoundException 
	{
		Optional<CmisUserEntity> user = userRepo.findById(id);
		
		if(user.isPresent()) {
			return user.get();
		} else {
			throw new RecordNotFoundException("No user record exist for given id");
		}
	}

	public CmisUserEntity createOrUpdateUser(CmisUserEntity entity) {
		if (entity.getId() == null) {
			entity = userRepo.save(entity);
			return entity;
		} else {
			Optional<CmisUserEntity> user = userRepo.findById(entity.getId());

			if (user.isPresent()) {
				CmisUserEntity newEntity = user.get();
				newEntity.setUserName(entity.getUserName());
				newEntity.setUserPwd(entity.getUserPwd());
				newEntity.setCreateDate();

				newEntity = userRepo.save(newEntity);

				return newEntity;
			} else {
				entity = userRepo.save(entity);

				return entity;
			}
		}
	}

	public void deleteUserById(Long id) throws RecordNotFoundException {
		Optional<CmisUserEntity> user = userRepo.findById(id);

		if (user.isPresent()) {
			userRepo.deleteById(id);
		} else {
			throw new RecordNotFoundException("No user record exist for given id");
		}
	}
	
}
