package by.mainsoft.organization.client.service;

import java.util.List;

import by.mainsoft.organization.shared.domain.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/userService")
public interface UserService extends RemoteService {
	public Long create(User newInstance);

	public User read(Long id);

	void update(User transientObject);

	void delete(User persistentObject);

	public List<User> getAll();

	public List<User> searchByString(String searchParameter);
}
