package by.mainsoft.organization.server.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import by.mainsoft.organization.server.dao.CompanyDao;
import by.mainsoft.organization.shared.domain.Company;

@Repository
public class CompanyDaoImpl extends GenericDaoImpl<Company, Long> implements CompanyDao {

	@Override
	public Long create(Company newInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Company read(Class<Company> classT, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Company transientObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Company persistentObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Company> getAll(Class<Company> classT) {
		// TODO Auto-generated method stub
		return null;
	}

}
