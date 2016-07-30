package by.mainsoft.organization.client.ui;

import java.util.ArrayList;
import java.util.List;

import by.mainsoft.organization.client.service.TypeService;
import by.mainsoft.organization.client.service.TypeServiceAsync;
import by.mainsoft.organization.shared.domain.Type;
import by.mainsoft.organization.shared.domain.TypeProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

public class DirectoryBinding implements IsWidget, Editor<Type> {

	interface TypeDriver extends SimpleBeanEditorDriver<Type, DirectoryBinding> {
	}

	TypeServiceAsync typeService = GWT.create(TypeService.class);
	private FramedPanel panel;
	private ListStore<Type> typeStore;
	private TypeDriver driver = GWT.create(TypeDriver.class);
	private TypeProperties props;
	private Type type;
	private Grid<Type> grid;
	private Window typeWindow;
	// editor fields
	TextField name;

	@Override
	public Widget asWidget() {
		if (panel == null) {
			panel = new FramedPanel();
			panel.setHeadingText("Model with List Property");
			panel.setBodyBorder(false);
			// panel.setWidth(400);
			panel.addStyleName("margin-10");
			props = GWT.create(TypeProperties.class);
			typeStore = new ListStore<Type>(props.key());
			refreshTypeList();

			HorizontalPanel hp = new HorizontalPanel();
			hp.add(createEditor());

			panel.add(hp);

			driver.initialize(this);
			// nameCombo.setValue(company);
			driver.edit(type);

		}
		return panel;
	}

	public Widget createEditor() {
		VerticalPanel vertPan = new VerticalPanel();
		CssFloatLayoutContainer outer = new CssFloatLayoutContainer();
		ColumnConfig<Type, String> nameColumn = new ColumnConfig<Type, String>(props.name(), 200, "Name");
		List<ColumnConfig<Type, ?>> columns = new ArrayList<ColumnConfig<Type, ?>>();
		columns.add(nameColumn);
		grid = new Grid<Type>(typeStore, new ColumnModel<Type>(columns));
		// grid.setSelectionModel(GridSelectionModel<Type>);

		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.getView().setForceFit(true);
		grid.getView().setAutoExpandColumn(nameColumn);
		grid.setHideHeaders(true);
		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Type>() {

			@Override
			public void onSelectionChanged(SelectionChangedEvent<Type> event) {
				if (event.getSelection().size() > 0) {
					// edit(event.getSelection().get(0));
					// address.clearInvalid();
					// employee.clearInvalid();
					type = event.getSelection().get(0);
					driver.edit(type);
				}

			}
		});
		VerticalPanel typeDialogPanel = new VerticalPanel();
		CssFloatLayoutContainer typeouter = new CssFloatLayoutContainer();
		typeWindow = new Window();
		typeWindow.setPixelSize(200, 200);
		typeWindow.setResizable(false);
		typeWindow.setModal(true);
		typeWindow.setBlinkModal(true);
		typeWindow.setHeadingText("добавить тип");
		typeWindow.addHideHandler(new HideHandler() {
			@Override
			public void onHide(HideEvent event) {
				deleteType();
			}
		});
		name = new TextField();
		name.setEmptyText("наименование организации");
		name.setAllowBlank(false);
		FieldLabel nameFieldLabel = new FieldLabel(name, "тип");
		nameFieldLabel.setLabelSeparator("");
		typeouter.add(nameFieldLabel, new CssFloatData(1, new Margins(10, 10, 10, 10)));
		TextButton addTypeButton = new TextButton("добавить");
		addTypeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				type = driver.flush();
				if (driver.hasErrors()) {
					new MessageBox("Исправьте ошибки перед сохранением").show();
					return;
				}
				updateType(type);
				typeWindow.hide();
			}
		});
		typeDialogPanel.add(typeouter);
		typeWindow.add(typeDialogPanel);
		typeWindow.addButton(addTypeButton);

		TextButton addButton = new TextButton("добавить");
		addButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				typeStore.add(new Type());
				grid.getSelectionModel().select(typeStore.size() - 1, true);
				typeWindow.show();

			}
		});
		TextButton deleteButton = new TextButton("удалить");
		deleteButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				deleteType();
			}
		});
		outer.add(addButton);
		outer.add(deleteButton);
		outer.add(grid, new CssFloatData(1));
		vertPan.add(outer);
		return vertPan;
	}

	public void refreshTypeList() {
		typeService.getAll(new AsyncCallback<List<Type>>() {
			public void onFailure(Throwable caught) {
				Info.display("Ошибка", "Данные не обновлены");
				caught.printStackTrace();
			}

			public void onSuccess(List<Type> companyList) {
				typeStore.clear();
				typeStore.addAll(companyList);
				type = typeStore.get(0);
				Info.display("Ура!", type.getName());

				// companyListView.getSelectionModel().select(0, true);
			}
		});
	}

	void deleteType() {
		typeService.delete(type, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				Info.display("Ошибка", "Данные не обновлены");
				caught.printStackTrace();
			}

			public void onSuccess(Void result) {
				refreshTypeList();
				grid.getView().refresh(true);
			}
		});
	}

	void updateType(Type type) {
		typeService.update(type, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display("Ошибка", "Данные  компании не обновлены");
			}

			@Override
			public void onSuccess(Void result) {
				Info.display("ОК", "ок");
				refreshTypeList();
			}
		});
	}

}
