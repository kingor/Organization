package by.mainsoft.organization.client.ui;

import java.util.List;

import by.mainsoft.organization.client.service.CompanyService;
import by.mainsoft.organization.client.service.CompanyServiceAsync;
import by.mainsoft.organization.shared.domain.Company;
import by.mainsoft.organization.shared.domain.CompanyProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanelHelper;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MinNumberValidator;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

public class CompanyBinding implements IsWidget, Editor<Company> {

	interface CompanyDriver extends SimpleBeanEditorDriver<Company, CompanyBinding> {
	}

	private CompanyServiceAsync companyService = GWT.create(CompanyService.class);

	private FramedPanel panel;
	private Company company;
	private ListView<Company, String> companyListView;

	// editor fields
	TextField address;
	TextField name;
	TextField data;
	IntegerField employee;

	private ListStore<Company> companyStore;
	private CompanyDriver driver = GWT.create(CompanyDriver.class);
	private CompanyProperties props;

	public Widget asWidget() {
		if (panel == null) {
			props = GWT.create(CompanyProperties.class);

			companyStore = new ListStore<Company>(props.key());
			refreshCompanyList();

			company = companyStore.get(0);

			HorizontalPanel hp = new HorizontalPanel();
			hp.setSpacing(10);

			panel = new FramedPanel();
			panel.setHeadingHtml("Basic Binding Example");
			panel.setWidth(600);
			panel.setLayoutData(new MarginData(20));

			// hp.add(updateDisplay());
			hp.add(createEditor());

			panel.add(hp);

			// hp.setCellWidth(updateDisplay(), "200");

			driver.initialize(this);
			// nameCombo.setValue(company);
			driver.edit(company);
		}

		return panel;
	}

	private Widget createEditor() {
		// ContentPanel panel = new ContentPanel();
		HorizontalPanel buttonPanel = new HorizontalPanel();
		VerticalPanel listPanel = new VerticalPanel();
		HorizontalPanel horPanel = new HorizontalPanel();
		VerticalPanel panel = new VerticalPanel();
		VerticalPanel vartPan = new VerticalPanel();
		// panel.setWidth(400);
		// panel.setBodyStyle("padding: 5px;");
		// panel.setHeaderVisible(false);

		CssFloatLayoutContainer outer = new CssFloatLayoutContainer();

		companyListView = new ListView<Company, String>(companyStore, props.name());// new ComboBox<Company>(companyStore, props.nameLabel());
		companyListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		companyListView.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Company>() {
			@Override
			public void onSelectionChanged(SelectionChangedEvent<Company> event) {
				if (event.getSelection().size() > 0) {
					// edit(event.getSelection().get(0));
					address.clearInvalid();

					company = event.getSelection().get(0);
					driver.edit(company);
				} else {
					// stockEditor.setSaveEnabled(false);
				}
			}
		});

		TextButton addButton = new TextButton("Добавить");
		addButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				companyStore.add(new Company());
				companyListView.getSelectionModel().select(companyListView.getItemCount() - 1, true);
			}
		});

		TextButton deleteButton = new TextButton("Удалить");
		deleteButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				deleteCompany();
			}
		});

		buttonPanel.add(addButton);
		buttonPanel.add(deleteButton);
		listPanel.add(buttonPanel);

		// outer.add(new FieldLabel(nameCombo, "Select Company"), new CssFloatData(1));
		listPanel.add(companyListView);

		horPanel.add(listPanel);

		final CssFloatLayoutContainer inner = new CssFloatLayoutContainer();

		name = new TextField();
		name.setAllowBlank(false);

		inner.add(new FieldLabel(name, "Наименование"), new CssFloatData(1));

		address = new TextField();
		// address.addValidator(new RegExValidator("^[^a-z]+$", "Only uppercase letters allowed"));
		// address.setAutoValidate(true);
		address.setName("address");
		inner.add(new FieldLabel(address, "Адрес"), new CssFloatData(1));

		outer.add(inner, new CssFloatData(1));

		employee = new IntegerField();
		employee.setName("employee");
		employee.setFormat(NumberFormat.getFormat("0"));
		employee.setAllowNegative(false);
		employee.addValidator(new MinNumberValidator<Integer>(0));
		inner.add(new FieldLabel(employee, "Кол. сотрудников"), new CssFloatData(1));

		TextButton reset = new TextButton("Cancel");
		reset.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				FormPanelHelper.reset(inner);
				driver.edit(company);
			}
		});

		vartPan.add(reset);// addButton(reset);

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
				// companyStore.update(company);
				refreshCompanyList();
			}
		});
		vartPan.add(save);// addButton(save);

		vartPan.add(outer);
		horPanel.add(vartPan);
		panel.add(horPanel);
		return panel;
	}

	public void refreshCompanyList() {
		companyService.getAll(new AsyncCallback<List<Company>>() {
			public void onFailure(Throwable caught) {
				Window.alert("refresh Async callback не работает!");
				caught.printStackTrace();
			}

			public void onSuccess(List<Company> companyList) {
				companyStore.clear();
				companyStore.addAll(companyList);
				company = companyStore.get(0);
				companyListView.refresh();
			}
		});
	}

	void updateCompany(Company company) {
		companyService.update(company, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("create Async callback не работает!");
				caught.printStackTrace();

			}

			@Override
			public void onSuccess(Void result) {
				refreshCompanyList();
			}
		});
	}

	private void fillCompanyList(List<Company> companyList) {

	}

	void deleteCompany() {
		companyService.delete(company, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				Window.alert("delete Async callback не работает!");
				caught.printStackTrace();
			}

			public void onSuccess(Void result) {
				refreshCompanyList();
			}
		});
	}
}
