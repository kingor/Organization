package by.mainsoft.organization.server.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import by.mainsoft.organization.server.dao.UserDao;
import by.mainsoft.organization.shared.domain.User;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

	@Override
	public Long create(User newInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User read(Class<User> classT, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(User transientObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(User persistentObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> getAll(Class<User> classT) {
		// TODO Auto-generated method stub
		return null;
	}

}
