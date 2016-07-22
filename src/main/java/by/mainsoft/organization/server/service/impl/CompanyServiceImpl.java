package by.mainsoft.organization.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.mainsoft.organization.client.service.CompanyService;
import by.mainsoft.organization.server.dao.CompanyDao;
import by.mainsoft.organization.shared.domain.Company;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyDao companyDao;

	@Override
	@Transactional
	public Long create(Company newInstance) {
		return companyDao.create(newInstance);
	}

	@Override
	@Transactional
	public Company read(Long id) {
		return companyDao.read(Company.class, id);
	}

	@Override
	@Transactional
	public void update(Company transientObject) {
		companyDao.update(transientObject);
	}

	@Override
	@Transactional
	public void delete(Company persistentObject) {
		companyDao.delete(persistentObject);
	}

	@Override
	@Transactional
	public List<Company> getAll() {
		return companyDao.getAll(Company.class);
	}

}
