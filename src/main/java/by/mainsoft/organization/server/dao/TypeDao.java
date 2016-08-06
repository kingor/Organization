package by.mainsoft.organization.server.dao;

import java.util.List;

import by.mainsoft.organization.shared.domain.Type;

public interface TypeDao extends GenericDao<Type, Long> {

	public List<Type> searchByString(String searchParameter);

	public List<Type> searchByName(String name);
}
