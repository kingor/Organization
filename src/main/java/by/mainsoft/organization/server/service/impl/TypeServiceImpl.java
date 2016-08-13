package by.mainsoft.organization.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.mainsoft.organization.client.service.TypeService;
import by.mainsoft.organization.server.dao.CompanyDao;
import by.mainsoft.organization.server.dao.TypeDao;
import by.mainsoft.organization.shared.domain.Type;

@Service("typeService")
public class TypeServiceImpl implements TypeService {

	@Autowired
	TypeDao typeDao;

	@Autowired
	CompanyDao companyDao;

	private static final Logger logger = Logger.getLogger(TypeService.class.getName());

	@Override
	@Transactional
	public Long create(Type newInstance) {
		logger.info("SERVICE - caused create()");
		if (newInstance == null) {
			return -1L;
		}
		List<Type> typeList = typeDao.searchByName(newInstance.getName());
		if (typeList != null && typeList.size() > 0) {
			return -1L;
		}
		return typeDao.create(newInstance);
	}

	@Override
	@Transactional
	public Type read(Long id) {
		logger.info("SERVICE - caused read()");
		return typeDao.read(Type.class, id);
	}

	@Override
	@Transactional
	public void update(Type transientObject) {
		logger.info("SERVICE - caused update()");
		typeDao.update(transientObject);
	}

	@Override
	@Transactional
	public void delete(Type persistentObject) {
		logger.info("SERVICE - caused delete()");
		typeDao.delete(persistentObject);
	}

	@Override
	@Transactional
	public List<Type> getAll() {
		logger.info("SERVICE - caused getAll()");
		return typeDao.getAll(Type.class);
	}

	@Override
	@Transactional
	public List<Type> searchByString(String searchParameter) {
		logger.info("SERVICE - caused searchByString()");
		return typeDao.searchByString(searchParameter);
	}

}
