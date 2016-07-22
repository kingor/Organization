package by.mainsoft.organization.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.mainsoft.organization.client.service.TypeService;
import by.mainsoft.organization.server.dao.TypeDao;
import by.mainsoft.organization.shared.domain.Type;

@Service("typeService")
public class TypeServiceImpl implements TypeService {

	@Autowired
	TypeDao typeDao;

	@Override
	@Transactional
	public Long create(Type newInstance) {
		return typeDao.create(newInstance);
	}

	@Override
	@Transactional
	public Type read(Long id) {
		return typeDao.read(Type.class, id);
	}

	@Override
	@Transactional
	public void update(Type transientObject) {
		typeDao.update(transientObject);
	}

	@Override
	@Transactional
	public void delete(Type persistentObject) {
		typeDao.delete(persistentObject);
	}

	@Override
	@Transactional
	public List<Type> getAll() {
		return typeDao.getAll(Type.class);
	}

	@Override
	@Transactional
	public List<Type> searchByString(String searchParameter) {
		return typeDao.searchByString(searchParameter);
	}

}
