package by.mainsoft.organization.client.ui;

import by.mainsoft.organization.shared.domain.Company;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;

public class StockEditor implements IsWidget, Editor<Company> {

	private FormPanel panel;
	private VerticalLayoutContainer container;

	TextField name;
	TextField address;

	private TextButton save;

	public StockEditor() {
		panel = new FormPanel();
		panel.setLabelWidth(50);

		container = new VerticalLayoutContainer();
		panel.setWidget(container);

		name = new TextField();
		name.setEnabled(false);
		address = new TextField();
		address.setEnabled(false);

		container.add(new FieldLabel(name, "Name"), new VerticalLayoutData(1, -1));
		container.add(new FieldLabel(address, "Address"), new VerticalLayoutData(1, -1));

		save = new TextButton("Save");
		save.setEnabled(false);
		container.add(save);

		panel.setLabelWidth(50);
	}

	@Override
	public Widget asWidget() {
		return panel;
	}

	public void setSaveEnabled(boolean enabled) {
		save.setEnabled(enabled);
		name.setEnabled(enabled);
		address.setEnabled(enabled);
		if (!enabled) {

			name.setValue("");
			address.setValue("");
		}
	}

	public HasSelectHandlers getSaveButton() {
		return save;
	}
}