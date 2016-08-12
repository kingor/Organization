package by.mainsoft.organization.server.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import by.mainsoft.organization.server.dao.CompanyDao;
import by.mainsoft.organization.shared.domain.Company;

@Repository
public class CompanyDaoImpl extends GenericDaoImpl<Company, Long> implements CompanyDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static final Logger logger = Logger.getLogger(CompanyDao.class.getName());

	@Override
	public void setNullType(Long typeId) {
		logger.info("DAO - caused setNull()");
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("UPDATE Company SET name=:name");
		query.setParameter("name", "sdfsdf");
		int result = query.executeUpdate();
		logger.info(result);
	}
}
