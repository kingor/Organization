package by.mainsoft.organization.server.dao.impl;

import java.util.List;

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

	@Override
	public List<User> searchByString(String searchParameter) {
		Session session = sessionFactory.getCurrentSession();
		Disjunction disc = Restrictions.disjunction();
		disc.add(Restrictions.like("name", "%" + searchParameter + "%"));
		disc.add(Restrictions.like("surname", "%" + searchParameter + "%"));
		disc.add(Restrictions.like("patronimic", "%" + searchParameter + "%"));

		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) session.createCriteria(User.class).add(disc).list();
		return userList;
	}

}
