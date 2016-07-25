package by.mainsoft.organization.server.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.mainsoft.organization.server.dao.GenericDao;

@Repository
public class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public PK create(T newInstance) {
		Session session = sessionFactory.getCurrentSession();
		return (PK) session.save(newInstance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T read(Class<T> classT, PK id) {
		Session session = sessionFactory.getCurrentSession();
		T objectT = (T) session.createCriteria(classT).add(Restrictions.eq("id", id)).uniqueResult();
		return objectT;
	}

	@Override
	public void update(T transientObject) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(transientObject);
	}

	@Override
	public void delete(T persistentObject) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(persistentObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(Class<T> classT) {
		Session session = sessionFactory.getCurrentSession();
		List<T> all = (List<T>) session.createCriteria(classT).list();
		return all;
	}

}
