package by.mainsoft.organization.server.dao;

import java.util.List;

import by.mainsoft.organization.shared.domain.User;

public interface UserDao extends GenericDao<User, Long> {

	public List<User> searchByString(String searchParameter);
}
