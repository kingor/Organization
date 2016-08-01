package by.mainsoft.organization.client.ui;

import java.util.List;

import by.mainsoft.organization.client.service.CompanyService;
import by.mainsoft.organization.client.service.CompanyServiceAsync;
import by.mainsoft.organization.shared.domain.Company;
import by.mainsoft.organization.shared.domain.CompanyProperties;

import com.google.gwt.core.client.GWT;
//import com.google.gwt.dom.client.Style;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.DateTimePropertyEditor;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

public class CompanyBinding implements IsWidget, Editor<Company> {

	interface CompanyDriver extends SimpleBeanEditorDriver<Company, CompanyBinding> {
	}

	private CompanyServiceAsync companyService = GWT.create(CompanyService.class);
	private static final int HEIGHT = 450;

	private CssFloatLayoutContainer panel;
	private Company company;
	private ListView<Company, String> companyListView;
	Window typeWindow;
	private final Dialog userWindow = new Dialog();

	// editor fields
	TextField address;
	TextField name;
	TextArea data;
	TextField phone;
	IntegerField employee;
	TextArea info = new TextArea();
	TextField typeName;
	TextField userShortName;
	DateField date;

	private ListStore<Company> companyStore;
	private CompanyDriver driver = GWT.create(CompanyDriver.class);
	private CompanyProperties props;

	public Widget asWidget() {
		if (panel == null) {
			props = GWT.create(CompanyProperties.class);

			companyStore = new ListStore<Company>(props.key());
			refreshCompanyList();

			company = companyStore.get(0);

			panel = new CssFloatLayoutContainer();
			panel.setHeight(HEIGHT);
			panel.add(createEditor(), new CssFloatData(1));

			driver.initialize(this);
			driver.edit(company);
		}

		return panel;
	}

	private Widget createEditor() {
		CssFloatLayoutContainer buttonPanel = new CssFloatLayoutContainer();
		CssFloatLayoutContainer listPanel = new CssFloatLayoutContainer();
		CssFloatLayoutContainer scrollPanel = new CssFloatLayoutContainer();
		CssFloatLayoutContainer outer = new CssFloatLayoutContainer();

		companyListView = new ListView<Company, String>(companyStore, props.name());
		companyListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		companyListView.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Company>() {
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Company> event) {
				if (event.getSelection().size() > 0) {
					// edit(event.getSelection().get(0));
					address.clearInvalid();
					employee.clearInvalid();
					company = event.getSelection().get(0);
					driver.edit(company);
				}
			}
		});

		TextButton addButton = new TextButton("добавить");
		addButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				companyStore.add(new Company());
				companyListView.getSelectionModel().select(companyListView.getItemCount() - 1, true);
			}
		});

		TextButton deleteButton = new TextButton("удалить");
		deleteButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				deleteCompany();
			}
		});

		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		listPanel.add(buttonPanel, new CssFloatData(1, new Margins(15, 0, 10, 0)));
		listPanel.add(companyListView, new CssFloatData(1));
		listPanel.setBorders(true);
		outer.add(listPanel, new CssFloatData(0.25));

		/*
		 * Form
		 */

		final CssFloatLayoutContainer inner = new CssFloatLayoutContainer();

		/*
		 * Name row
		 */
		name = new TextField();
		name.setEmptyText("наименование организации");
		name.setAllowBlank(false);

		VerticalLayoutContainer nameRow = new VerticalLayoutContainer();
		nameRow.add(name, new VerticalLayoutData(1, -1, new Margins(0, 0, 0, 20)));
		inner.add(nameRow, new CssFloatData(1, new Margins(0, 0, 30, 0)));

		/*
		 * Data row
		 */

		data = new TextArea();
		data.setName("data");
		data.setHeight(70);
		FieldLabel dataLabel = new FieldLabel(data, "сведения");
		dataLabel.setLabelSeparator("");
		inner.add(dataLabel, new CssFloatData(0.5));

		/*
		 * Address field
		 */

		address = new TextField();
		address.setName("address");
		FieldLabel addressLabel = new FieldLabel(address, "адрес");
		addressLabel.setLabelSeparator("");
		inner.add(addressLabel, new CssFloatData(0.5));

		/*
		 * Phone field
		 */

		phone = new TextField();
		phone.setName("phone");
		inner.add(new FieldLabel(phone, "телефон"), new CssFloatData(0.5, new Margins(5, 0, 0, 0)));

		/*
		 * Employee field
		 */

		CssFloatLayoutContainer employeePanel = new CssFloatLayoutContainer();
		employee = new IntegerField();
		employee.setName("employee");
		name.setAllowBlank(false);
		employee.setFormat(NumberFormat.getFormat("0"));
		employee.setAllowNegative(false);
		employee.addValidator(new MinNumberValidator<Integer>(0));
		FieldLabel emplFieldLabel = new FieldLabel(employee, "кол. сотрудников");
		emplFieldLabel.setLabelSeparator("");
		employeePanel.add(emplFieldLabel, new CssFloatData(0.35));
		inner.add(employeePanel, new CssFloatData(1, new Margins(10, 0, 10, 0)));

		/*
		 * Info field
		 */
		info = new TextArea();
		info.setName("info");
		info.setHeight(70);
		inner.add(new FieldLabel(info, "доп. информация"), new CssFloatData(1, new Margins(0, 0, 10, 0)));

		/*
		 * Line
		 */

		HTML html = new HTML("<hr  style=\"width:100%;\" />");
		inner.add(html, new CssFloatData(1, new Margins(0, 0, 10, 0)));

		/*
		 * Type row
		 */

		typeName = new TextField();
		typeName.setName("typeName");
		inner.add(new FieldLabel(typeName, "тип"), new CssFloatData(0.35));
		TextButton typeButton = new TextButton("...");

		typeButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				final ChooseType chooseType = new ChooseType();
				typeWindow = new Window();
				typeWindow.setPixelSize(300, 200);
				typeWindow.setResizable(false);
				typeWindow.setModal(true);
				typeWindow.setBlinkModal(true);
				typeWindow.setHeadingText("выбрать тип");
				typeWindow.setExpanded(true);
				HTML line = new HTML("<hr  style=\"width:100%;\" />");
				typeWindow.add(line);
				typeWindow.add(chooseType);
				chooseType.getSaveButton().addSelectHandler(new SelectHandler() {

					@Override
					public void onSelect(SelectEvent event) {
						company.setType(chooseType.getType());
						typeWindow.hide();
						typeName.setText(chooseType.getType().getName());
					}
				});
				typeWindow.show();
			}
		});
		inner.add(typeButton, new CssFloatData(0.05));

		/*
		 * User row
		 */

		CssFloatLayoutContainer userPanel = new CssFloatLayoutContainer();
		userShortName = new TextField();
		userShortName.setName("userShortName");
		userPanel.add(new FieldLabel(userShortName, "тип"), new CssFloatData(0.35));

		TextButton managerButton = new TextButton("...");

		managerButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				createUserDialogWindow();
				userWindow.show();
			}
		});
		userPanel.add(managerButton, new CssFloatData(0.05, new Margins(0, 30, 0, 0)));

		/*
		 * Date field
		 */

		date = new DateField(new DateTimePropertyEditor("MM/dd/yyyy"));
		date.setName("date");
		date.setAutoValidate(true);
		userPanel.add(date, new CssFloatData(0.15));
		inner.add(userPanel, new CssFloatData(1, new Margins(10, 30, 20, 0)));

		/*
		 * Save button
		 */

		TextButton save = new TextButton("Сохранить");
		save.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				company = driver.flush();
				if (driver.hasErrors()) {
					new MessageBox("Исправьте ошибки перед сохранением").show();
					return;
				}
				updateCompany(company);
				refreshCompanyList();
			}
		});
		CssFloatLayoutContainer savePanel = new CssFloatLayoutContainer();
		savePanel.setStyleFloat(com.google.gwt.dom.client.Style.Float.RIGHT);
		savePanel.add(save);// addButton(save);
		inner.add(savePanel, new CssFloatData(0.95));
		CssFloatLayoutContainer container = new CssFloatLayoutContainer();
		container.add(inner, new CssFloatData(0.95, new Margins(15, 0, 10, 10)));
		outer.add(container, new CssFloatData(0.75));

		return outer;
	}

	public void refreshCompanyList() {
		companyService.getAll(new AsyncCallback<List<Company>>() {
			public void onFailure(Throwable caught) {
				Info.display("Ошибка", "Данные не обновлены");
				caught.printStackTrace();
			}

			public void onSuccess(List<Company> companyList) {
				companyStore.clear();
				companyStore.addAll(companyList);
				company = companyStore.get(0);
				companyListView.refresh();
				companyListView.getSelectionModel().select(0, true);
			}
		});
	}

	void updateCompany(Company company) {
		companyService.update(company, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Ошибка", "Данные  компании не обновлены");
			}

			@Override
			public void onSuccess(Void result) {
				refreshCompanyList();
			}
		});
	}

	void deleteCompany() {
		companyService.delete(company, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				Info.display("Ошибка", "Данные  компании не обновлены");
				caught.printStackTrace();
			}

			public void onSuccess(Void result) {
				refreshCompanyList();
			}
		});
	}

	void createUserDialogWindow() {

		userWindow.setHeadingText("выбрать сотрудника");
		userWindow.setWidth(300);
		userWindow.setHeight(300);
		userWindow.setResizable(false);
		userWindow.setHideOnButtonClick(true);
		userWindow.setBodyStyleName("pad-text");
		userWindow.getBody().addClassName("pad-text");
		userWindow.add(new TextButton());
	}
}
