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

	ValueProvider<Company, Double> open();

	ValueProvider<Company, Double> last();

	ValueProvider<Company, Double> change();

	ValueProvider<Company, Date> lastTrans();

	ValueProvider<Company, Boolean> split();

	ValueProvider<Company, String> industry();
}