package by.mainsoft.organization.server.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.mainsoft.organization.server.dao.UserDao;
import by.mainsoft.organization.shared.domain.User;

@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = Logger.getLogger(UserDao.class.getName());

	@Override
	public List<User> searchByString(String searchParameter) {
		logger.info("DAO - caused searchByString()");
		Session session = sessionFactory.getCurrentSession();

		Disjunction disc = Restrictions.disjunction();
		disc.add(Restrictions.like("name", "%" + searchParameter + "%").ignoreCase());
		disc.add(Restrictions.like("surname", "%" + searchParameter + "%").ignoreCase());
		disc.add(Restrictions.like("patronymic", "%" + searchParameter + "%").ignoreCase());

		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) session.createCriteria(User.class).add(disc).list();// .add(Restrictions.like("name", "%" + searchParameter + "%").ignoreCase()).list();
		// logger.info(userList);
		return userList;
	}

	// logger.info(searchParameter);
}
