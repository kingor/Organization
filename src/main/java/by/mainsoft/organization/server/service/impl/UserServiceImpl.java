package by.mainsoft.organization.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.mainsoft.organization.client.service.UserService;
import by.mainsoft.organization.server.dao.UserDao;
import by.mainsoft.organization.shared.domain.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Override
	@Transactional
	public Long create(User newInstance) {
		return userDao.create(newInstance);
	}

	@Override
	@Transactional
	public User read(Long id) {
		return userDao.read(User.class, id);
	}

	@Override
	@Transactional
	public void update(User transientObject) {
		userDao.update(transientObject);
	}

	@Override
	@Transactional
	public void delete(User persistentObject) {
		userDao.delete(persistentObject);
	}

	@Override
	@Transactional
	public List<User> getAll() {
		return userDao.getAll(User.class);
	}

}
