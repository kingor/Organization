package by.mainsoft.organization.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
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

	private static final Logger logger = Logger.getLogger(UserService.class.getName());

	@Override
	@Transactional
	public Long create(User newInstance) {
		logger.info("SERVICE - caused create()");
		return userDao.create(newInstance);
	}

	@Override
	@Transactional
	public User read(Long id) {
		logger.info("SERVICE - caused read()");
		return userDao.read(User.class, id);
	}

	@Override
	@Transactional
	public void update(User transientObject) {
		logger.info("SERVICE - caused update()");
		userDao.update(transientObject);
	}

	@Override
	@Transactional
	public void delete(User persistentObject) {
		logger.info("SERVICE - caused dalete()");
		userDao.delete(persistentObject);
	}

	@Override
	@Transactional
	public List<User> getAll() {
		logger.info("SERVICE - caused getAll()");
		return userDao.getAll(User.class);
	}

	@Override
	@Transactional
	public List<User> searchByString(String searchParameter) {
		logger.info("SERVICE - caused searchByString()");
		return userDao.searchByString(searchParameter);
	}

}
