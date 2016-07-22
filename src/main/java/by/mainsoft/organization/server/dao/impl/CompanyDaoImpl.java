package by.mainsoft.organization.server.dao.impl;

import org.springframework.stereotype.Repository;

import by.mainsoft.organization.server.dao.CompanyDao;
import by.mainsoft.organization.shared.domain.Company;

@Repository
public class CompanyDaoImpl extends GenericDaoImpl<Company, Long> implements CompanyDao {

}
