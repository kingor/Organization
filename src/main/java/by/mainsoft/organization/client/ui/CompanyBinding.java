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
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.theme.base.client.field.FieldLabelDefaultAppearance.FieldLabelResources;
import com.sencha.gxt.theme.base.client.field.FieldLabelDefaultAppearance.Style;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.container.MarginData;
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

	public interface RtJstFieldLabelResources extends FieldLabelResources, ClientBundle {

		@Source({ "com/sencha/gxt/theme/base/client/field/FieldLabel.css", "RightJustifiedFieldLabel.css" })
		MyStyle css();
	}

	public interface MyStyle extends Style {
	}

	private CompanyServiceAsync companyService = GWT.create(CompanyService.class);

	private FramedPanel panel;
	private Company company;
	private ListView<Company, String> companyListView;

	final Dialog typeWindow = new Dialog();

	final Dialog userWindow = new Dialog();

	// editor fields
	TextField address;
	TextField name;
	TextArea data;
	TextField phone;
	IntegerField employee;
	TextArea info = new TextArea();
	// TextField type;
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

			HorizontalPanel hp = new HorizontalPanel();
			// hp.setSpacing(10);

			panel = new FramedPanel();
			// panel.setWidth(600);
			panel.setLayoutData(new MarginData(0));
			// hp.add(updateDisplay());
			hp.add(createEditor());

			panel.add(hp);

			driver.initialize(this);
			// nameCombo.setValue(company);
			driver.edit(company);
		}

		return panel;
	}

	private Widget createEditor() {
		// ContentPanel panel = new ContentPanel();
		CssFloatLayoutContainer buttonPanel = new CssFloatLayoutContainer();
		CssFloatLayoutContainer listPanel = new CssFloatLayoutContainer();

		VerticalPanel panel = new VerticalPanel();
		VerticalPanel vartPan = new VerticalPanel();
		VerticalLayoutContainer vc = new VerticalLayoutContainer();
		// panel.setWidth(400);
		// panel.setBodyStyle("padding: 5px;");
		// panel.setHeaderVisible(false);

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
		listPanel.add(buttonPanel, new CssFloatData(1, new Margins(15, 0, 10, 0)));
		vc.add(companyListView, new VerticalLayoutData(1, 1));
		listPanel.add(vc, new CssFloatData(1));
		listPanel.setBorders(true);

		outer.add(listPanel, new CssFloatData(0.2));

		final CssFloatLayoutContainer inner = new CssFloatLayoutContainer();

		name = new TextField();
		name.setEmptyText("наименование организации");
		name.setAllowBlank(false);

		inner.add(name, new CssFloatData(1, new Margins(15, 0, 30, 20)));

		data = new TextArea();
		data.setName("data");
		data.setHeight(70);
		FieldLabel dataLabel = new FieldLabel(data, "сведения");
		dataLabel.setLabelSeparator("");
		inner.add(dataLabel, new CssFloatData(0.5));

		address = new TextField();
		address.setName("address");
		FieldLabel addressLabel = new FieldLabel(address, "адрес");
		addressLabel.setLabelSeparator("");
		inner.add(addressLabel, new CssFloatData(0.5));

		phone = new TextField();
		phone.setName("phone");
		inner.add(new FieldLabel(phone, "телефон"), new CssFloatData(0.5));

		employee = new IntegerField();
		employee.setName("employee");
		name.setAllowBlank(false);
		employee.setFormat(NumberFormat.getFormat("0"));
		employee.setAllowNegative(false);
		employee.addValidator(new MinNumberValidator<Integer>(0));
		FieldLabel emplFieldLabel = new FieldLabel(employee, "кол. сотрудников");
		emplFieldLabel.setWidth(200);
		emplFieldLabel.setLabelSeparator("");
		inner.add(emplFieldLabel, new CssFloatData(0.35, new Margins(10, 300, 0, 0)));

		info = new TextArea();
		info.setName("info");
		info.setHeight(70);
		inner.add(new FieldLabel(info, "доп. информация"), new CssFloatData(1));

		// type = new TextField();
		// type.setName("type.toString");
		// inner.add(new FieldLabel(type, "тип"), new CssFloatData(0.35));
		TextButton typeButton = new TextButton("...");

		typeButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				createTypeDialogWindow();
				typeWindow.show();
			}
		});

		inner.add(typeButton, new CssFloatData(0.05, new Margins(0, 600, 0, 0)));
		TextButton managerButton = new TextButton("...");

		managerButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				createUserDialogWindow();
				userWindow.show();
			}
		});

		inner.add(managerButton, new CssFloatData(0.05, new Margins(0, 50, 0, 0)));
		date = new DateField(new DateTimePropertyEditor("MM/dd/yyyy"));
		date.setName("date");
		date.setAutoValidate(true);
		inner.add(date, new CssFloatData(0.15));

		outer.add(inner, new CssFloatData(0.8, new Margins(0, 0, 0, 0)));

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
		outer.add(save, new CssFloatData(0.1, new Margins(10, 0, 0, 550)));// addButton(save);
		vartPan.add(outer);

		// horPanel.add(vartPan);
		panel.add(outer);
		return panel;
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
				Window.alert("delete Async callback не работает!");
				caught.printStackTrace();
			}

			public void onSuccess(Void result) {
				refreshCompanyList();
			}
		});
	}

	void createTypeDialogWindow() {

		typeWindow.setHeadingText("выбрать тип");
		typeWindow.setWidth(300);
		typeWindow.setHeight(300);
		typeWindow.setResizable(false);
		typeWindow.setHideOnButtonClick(true);
		typeWindow.setPredefinedButtons(PredefinedButton.YES);
		typeWindow.setBodyStyleName("pad-text");
		typeWindow.getBody().addClassName("pad-text");
		typeWindow.add(new TextButton());
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
