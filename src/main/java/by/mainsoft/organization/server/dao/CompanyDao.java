package by.mainsoft.organization.server.dao;

import by.mainsoft.organization.shared.domain.Company;
import by.mainsoft.organization.shared.domain.Type;
import by.mainsoft.organization.shared.domain.User;

public interface CompanyDao extends GenericDao<Company, Long> {

	public void setNullType(Type type);

	public void setNullUser(User user);
}
