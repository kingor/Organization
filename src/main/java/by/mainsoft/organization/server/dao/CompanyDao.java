package by.mainsoft.organization.server.dao;

import by.mainsoft.organization.shared.domain.Company;

public interface CompanyDao extends GenericDao<Company, Long> {

	public void setNullType(Long typeId);
}
