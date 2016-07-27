package by.mainsoft.organization.client.ui;

import java.util.List;

import by.mainsoft.organization.client.service.CompanyService;
import by.mainsoft.organization.client.service.CompanyServiceAsync;
import by.mainsoft.organization.client.service.TypeService;
import by.mainsoft.organization.client.service.TypeServiceAsync;
import by.mainsoft.organization.shared.FieldVerifier;
import by.mainsoft.organization.shared.domain.Company;
import by.mainsoft.organization.shared.domain.Type;
import by.mainsoft.organization.shared.domain.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class OrganizationTabView extends Composite {

	private CompanyServiceAsync companyService = GWT.create(CompanyService.class);
	private TypeServiceAsync typeService = GWT.create(TypeService.class);
	private static OrganizationTabViewUiBinder uiBinder = GWT.create(OrganizationTabViewUiBinder.class);

	private List<Company> companyAll;

	@UiField
	ListBox organizationList;

	interface OrganizationTabViewUiBinder extends UiBinder<Widget, OrganizationTabView> {
	}

	public OrganizationTabView() {
		initWidget(uiBinder.createAndBindUi(this));
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
	IntegerBox employeeField;
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
	DateBox dateField;

	@UiField
	Button saveButton;

	@UiHandler("addButton")
	void onAddClick(ClickEvent e) {
		Company company = new Company();
		companyAll.add(company);
		organizationList.addItem("");
	}

	@UiHandler("saveButton")
	void onSaveClick(ClickEvent e) {
		if (!FieldVerifier.isValidName(nameField.getText())) {
			Window.alert("Поле название должно быть заполнено!");
			return;
		}

		Company company = companyAll.get(organizationList.getSelectedIndex());
		company.setName(nameField.getText());
		company.setData(dataField.getText());
		company.setAddress(addressField.getText());
		company.setPhone(phoneField.getText());
		// if(employeeField.getText())
		company.setInfo(infoField.getText());
		company.setDate(dateField.getValue());

		companyService.create(company, new AsyncCallback<Long>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("create Async callback не работает!");
				caught.printStackTrace();

			}

			@Override
			public void onSuccess(Long result) {
				refreshCompanyList();

			}
		});
	}

	@UiHandler("deleteButton")
	void onDeleteClick(ClickEvent e) {
		Company company = companyAll.get(organizationList.getSelectedIndex());
		companyService.delete(company, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				Window.alert("delete Async callback не работает!");
				caught.printStackTrace();
			}

			public void onSuccess(Void result) {
				companyAll.remove(organizationList.getSelectedIndex());
				organizationList.removeItem(organizationList.getSelectedIndex());
				refreshCompanyList();
			}
		});
	}

	@UiHandler("organizationList")
	void onOrganizationChange(ChangeEvent event) {
		selectOrganization(organizationList.getSelectedIndex());
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
		companyAll = companyList;
		for (Company company : companyList) {
			organizationList.addItem(company.getName());
		}
		if (companyList.size() > 0) {
			organizationList.setSelectedIndex(0);
			selectOrganization(0);
		}
	}

	private void selectOrganization(int selectedRow) {
		if (!companyAll.isEmpty()) {
			Company company = companyAll.get(selectedRow);
			nameField.setText(company.getName());
			dataField.setText(company.getData());
			addressField.setText(company.getAddress());
			phoneField.setText(company.getPhone());
			employeeField.setText(String.valueOf(company.getEmployee()));
			infoField.setText(company.getInfo());

			dateField.setValue(company.getDate());
			DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
			dateField.setFormat(new DateBox.DefaultFormat(dateFormat));

			Type type = company.getType();
			if (!(type == null)) {
				typeField.setText(type.getName());
			} else
				typeField.setText("");
			User manager = company.getManager();
			if (!(manager == null)) {
				managerField.setText(manager.getShortName());
			} else
				managerField.setText("");

		} else
			Window.alert("Нет организаций в базе");
	}
}
