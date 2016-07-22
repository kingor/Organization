package by.mainsoft.organization.server.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import by.mainsoft.organization.server.dao.TypeDao;
import by.mainsoft.organization.shared.domain.Type;

@Repository
public class TypeDaoImpl extends GenericDaoImpl<Type, Long> implements TypeDao {

	@Override
	public Long create(Type newInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type read(Class<Type> classT, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Type transientObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Type persistentObject) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Type> getAll(Class<Type> classT) {
		// TODO Auto-generated method stub
		return null;
	}

}
