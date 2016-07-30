package by.mainsoft.organization.shared.domain;

import java.util.Date;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface CompanyProperties extends PropertyAccess<Company> {
	@Path("id")
	ModelKeyProvider<Company> key();

	@Path("name")
	LabelProvider<Company> nameLabel();

	ValueProvider<Company, String> name();

	ValueProvider<Company, String> address();

	ValueProvider<Company, String> data();

	ValueProvider<Company, Integer> employee();

	ValueProvider<Company, String> phone();

	ValueProvider<Company, String> info();

	ValueProvider<Company, Date> date();

}
