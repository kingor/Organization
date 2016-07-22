package by.mainsoft.organization.client.service;

import java.util.List;

import by.mainsoft.organization.shared.domain.Type;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/typeService")
public interface TypeService extends RemoteService {
	public Long create(Type newInstance);

	public Type read(Long id);

	void update(Type transientObject);

	void delete(Type persistentObject);

	public List<Type> getAll();

	public List<Type> searchByString(String searchParameter);

}
