package de.cmis.test.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.cmis.test.exceptions.RecordNotFoundException;
import de.cmis.test.model.CmisBindingEntity;
import de.cmis.test.repository.CmisBindingRepository;

@Service
public class CmisBindingService {

	@Autowired
	CmisBindingRepository bindingRepo;

	public List<CmisBindingEntity> getAllBindings() {
		List<CmisBindingEntity> result = (List<CmisBindingEntity>) bindingRepo.findAll();

		if (result.size() > 0) {
			return result;
		} else {
			return new ArrayList<CmisBindingEntity>();
		}
	}

	public CmisBindingEntity getBindingById(Long id) throws RecordNotFoundException {
		Optional<CmisBindingEntity> binding = bindingRepo.findById(id);

		if (binding.isPresent()) {
			return binding.get();
		} else {
			throw new RecordNotFoundException("No binding record exist for given id");
		}
	}

	public CmisBindingEntity createOrUpdateBinding(CmisBindingEntity entity) {
		if (entity.getId() == null) {
			entity = bindingRepo.save(entity);
			return entity;
		} else {
			Optional<CmisBindingEntity> binding = bindingRepo.findById(entity.getId());

			if (binding.isPresent()) {
				CmisBindingEntity newEntity = binding.get();
				newEntity.setBindingName(entity.getBindingName());
				newEntity.setBindingUrl(entity.getBindingUrl());
				newEntity.setCreateDate();

				newEntity = bindingRepo.save(newEntity);

				return newEntity;
			} else {
				entity = bindingRepo.save(entity);

				return entity;
			}
		}
	}

	public void deleteBindingById(Long id) throws RecordNotFoundException {
		Optional<CmisBindingEntity> user = bindingRepo.findById(id);

		if (user.isPresent()) {
			bindingRepo.deleteById(id);
		} else {
			throw new RecordNotFoundException("No binding record exist for given id");
		}
	}

}
