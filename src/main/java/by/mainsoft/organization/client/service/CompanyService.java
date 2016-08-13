package by.mainsoft.organization.client.service;

import java.util.List;

import by.mainsoft.organization.shared.domain.Company;
import by.mainsoft.organization.shared.domain.Type;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/companyService")
public interface CompanyService extends RemoteService {
	public Long create(Company newInstance);

	public Company read(Long id);

	void update(Company transientObject);

	void delete(Company persistentObject);

	public List<Company> getAll();

	public void setTypeNull(Type persistentObject);
}
