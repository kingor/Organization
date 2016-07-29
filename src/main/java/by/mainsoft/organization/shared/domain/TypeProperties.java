package by.mainsoft.organization.shared.domain;

import com.google.gwt.editor.client.Editor.Path;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface TypeProperties extends PropertyAccess<Type> {
	@Path("id")
	ModelKeyProvider<Type> key();

	ValueProvider<Type, String> name();

}
