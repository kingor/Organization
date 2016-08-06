package by.mainsoft.organization.server.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.mainsoft.organization.server.dao.TypeDao;
import by.mainsoft.organization.shared.domain.Type;

@Repository
public class TypeDaoImpl extends GenericDaoImpl<Type, Long> implements TypeDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = Logger.getLogger(TypeDao.class.getName());

	@Override
	public List<Type> searchByString(String searchParameter) {
		logger.info("DAO - caused searchByString()");
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Type> typeList = (List<Type>) session.createCriteria(Type.class).add(Restrictions.like("name", "%" + searchParameter + "%").ignoreCase()).list();
		return typeList;
	}

	@Override
	public List<Type> searchByName(String name) {
		logger.info("DAO - caused searchByName()");
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Type> typeList = (List<Type>) session.createCriteria(Type.class).add(Restrictions.eq("name", name).ignoreCase()).list();
		return typeList;
	}

}
