package by.mainsoft.organization.server.dao.impl;

import java.util.List;

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

	@Override
	public List<Type> searchByString(String searchParameter) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Type> typeList = (List<Type>) session.createCriteria(Type.class).add(Restrictions.like("name", "%" + searchParameter + "%")).list();
		return typeList;
	}

}
