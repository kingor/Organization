package by.mainsoft.organization.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.mainsoft.organization.client.service.CompanyService;
import by.mainsoft.organization.server.dao.CompanyDao;
import by.mainsoft.organization.shared.domain.Company;
import by.mainsoft.organization.shared.domain.Type;
import by.mainsoft.organization.shared.domain.User;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyDao companyDao;

	private static final Logger logger = Logger.getLogger(CompanyService.class.getName());

	@Override
	@Transactional
	public Long create(Company newInstance) {
		logger.info("SERVICE - caused create()");
		return companyDao.create(newInstance);
	}

	@Override
	@Transactional
	public Company read(Long id) {
		logger.info("SERVICE - caused read()");
		return companyDao.read(Company.class, id);
	}

	@Override
	@Transactional
	public void update(Company transientObject) {
		logger.info("SERVICE - caused update()");
		companyDao.update(transientObject);
	}

	@Override
	@Transactional
	public void delete(Company persistentObject) {
		logger.info("SERVICE - caused delete()");
		companyDao.delete(persistentObject);
	}

	@Override
	@Transactional
	public List<Company> getAll() {
		logger.info("SERVICE - caused getAll()");
		return companyDao.getAll(Company.class);
	}

	@Override
	@Transactional
	public void setTypeNull(Type persistentObject) {
		companyDao.setNullType(persistentObject);
	}

	@Override
	@Transactional
	public void setUserNull(User persistentObject) {
		companyDao.setNullUser(persistentObject);
	}

}
