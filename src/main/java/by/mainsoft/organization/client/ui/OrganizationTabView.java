package by.mainsoft.organization.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class OrganizationTabView extends Composite {

	private static OrganizationTabViewUiBinder uiBinder = GWT.create(OrganizationTabViewUiBinder.class);

	@UiField
	ListBox organizationList;

	interface OrganizationTabViewUiBinder extends UiBinder<Widget, OrganizationTabView> {
	}

	public OrganizationTabView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button addButton;
	@UiField
	Button deleteButton;

	@UiHandler("addButton")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

}
