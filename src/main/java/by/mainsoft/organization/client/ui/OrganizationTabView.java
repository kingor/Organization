package by.mainsoft.organization.client.ui;

import java.util.List;

import by.mainsoft.organization.client.service.CompanyService;
import by.mainsoft.organization.client.service.CompanyServiceAsync;
import by.mainsoft.organization.shared.FieldVerifier;
import by.mainsoft.organization.shared.domain.Company;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class OrganizationTabView extends Composite {

	private CompanyServiceAsync companyService = GWT.create(CompanyService.class);
	private static OrganizationTabViewUiBinder uiBinder = GWT.create(OrganizationTabViewUiBinder.class);

	@UiField
	ListBox organizationList;

	interface OrganizationTabViewUiBinder extends UiBinder<Widget, OrganizationTabView> {
	}

	public OrganizationTabView() {
		initWidget(uiBinder.createAndBindUi(this));
		organizationList.addItem("12345678912345678900");
		organizationList.addItem("dfgdfg");
		organizationList.addItem("12345678912345678900");
		organizationList.addItem("dfgdfg");
		organizationList.addItem("12345678912345678900");
		organizationList.addItem("dfgdfg");
		organizationList.addItem("12345678912345678900");
		organizationList.addItem("dfgdfg");
		organizationList.addItem("12345678912345678900");
		organizationList.addItem("dfgdfg");
		organizationList.addItem("12345678912345678900");
		organizationList.addItem("dfgdfg");
		organizationList.addItem("12345678912345678900");

		refreshCompanyList();
	}

	@UiField
	Button addButton;
	@UiField
	Button deleteButton;

	@UiField
	TextBox nameField;
	@UiField
	TextArea dataField;
	@UiField
	TextBox addressField;
	@UiField
	TextBox phoneField;
	@UiField
	TextBox employeeField;
	@UiField
	TextArea infoField;
	@UiField
	TextBox typeField;
	@UiField
	Button typeButton;
	@UiField
	TextBox managerField;
	@UiField
	Button managerButton;

	@UiField
	Button saveButton;

	@UiHandler("addButton")
	void onAddClick(ClickEvent e) {
		Window.alert("Hello!");
	}

	@UiHandler("saveButton")
	void onSaveClick(ClickEvent e) {
		if (!FieldVerifier.isValidName(nameField.getText())) {
			Window.alert("Поле название должно быть заполнено!");
			return;
		}
	}

	@UiHandler("employeeField")
	void onEmployeeKeyPress(KeyPressEvent e) {
		if (!FieldVerifier.isValidNumber(employeeField.getText())) {
			Window.alert("Поле количество сотрудников должно быть числовым!");
			employeeField.cancelKey();
		}
	}

	void refreshCompanyList() {
		companyService.getAll(new AsyncCallback<List<Company>>() {
			public void onFailure(Throwable caught) {
				Window.alert("refresh Async callback не работает!");
				caught.printStackTrace();
			}

			public void onSuccess(List<Company> companyList) {
				// logger.info("Async callback is working");
				fillCompanyList(companyList);
			}
		});
	}

	private void fillCompanyList(List<Company> companyList) {
		organizationList.clear();
		for (Company company : companyList) {
			organizationList.addItem(company.getName());
		}
	}
}
