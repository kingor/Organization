package by.mainsoft.organization.shared.domain;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface UserProperty extends PropertyAccess<User> {
	@Path("id")
	ModelKeyProvider<User> key();

	ValueProvider<User, String> surname();

	ValueProvider<User, String> name();

	ValueProvider<User, String> patronimic();
}
