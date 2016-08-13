package by.mainsoft.organization.client.ui;

import java.util.ArrayList;
import java.util.List;

import by.mainsoft.organization.client.Organization;
import by.mainsoft.organization.client.service.CompanyService;
import by.mainsoft.organization.client.service.CompanyServiceAsync;
import by.mainsoft.organization.client.service.UserService;
import by.mainsoft.organization.client.service.UserServiceAsync;
import by.mainsoft.organization.shared.domain.User;
import by.mainsoft.organization.shared.domain.UserProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
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

public class UserWidget implements IsWidget, Editor<User> {

	interface UserDriver extends SimpleBeanEditorDriver<User, UserWidget> {
	}

	UserServiceAsync userService = GWT.create(UserService.class);
	CompanyServiceAsync companyService = GWT.create(CompanyService.class);
	private ListStore<User> userStore;
	private UserDriver driver = GWT.create(UserDriver.class);
	private UserProperties props;
	private User user;
	private Grid<User> grid;
	private Window userWindow;
	private CustomTextButton deleteButton;

	// editor fields
	TextField surname;
	TextField name;
	TextField patronymic;

	private VerticalLayoutContainer container;

	@Override
	public Widget asWidget() {
		if (container == null) {
			container = new VerticalLayoutContainer();
			container.setHeight(DirectoryBinding.HEIGHT);
			props = GWT.create(UserProperties.class);
			userStore = new ListStore<User>(props.key());
			refreshUserList();
			container.add(createEditor());

			driver.initialize(this);
			driver.edit(user);

		}
		return container;
	}

	public Widget createEditor() {

		CssFloatLayoutContainer buttons = new CssFloatLayoutContainer();
		VerticalLayoutContainer outer = new VerticalLayoutContainer();
		CssFloatLayoutContainer gridPanel = new CssFloatLayoutContainer();
		ColumnConfig<User, String> surnameColumn = new ColumnConfig<User, String>(props.surname(), 200, "Фамилия");
		ColumnConfig<User, String> nameColumn = new ColumnConfig<User, String>(props.name(), 100, "Имя");
		ColumnConfig<User, String> patronymicColumn = new ColumnConfig<User, String>(props.patronymic(), 200, "Отчество");
		List<ColumnConfig<User, ?>> columns = new ArrayList<ColumnConfig<User, ?>>();
		columns.add(surnameColumn);
		columns.add(nameColumn);
		columns.add(patronymicColumn);

		grid = new Grid<User>(userStore, new ColumnModel<User>(columns));

		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.getView().setForceFit(true);
		grid.getView().setAutoExpandColumn(nameColumn);
		grid.setBorders(true);
		grid.setHeight(270);
		grid.getView().setStripeRows(true);
		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<User>() {

			@Override
			public void onSelectionChanged(SelectionChangedEvent<User> event) {
				if (event.getSelection().size() > 0) {
					user = event.getSelection().get(0);
					driver.edit(user);
					deleteButton.setEnabled(true);
				}

			}
		});

		createWindow();

		CustomTextButton addButton = new CustomTextButton("добавить");
		addButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				userStore.add(new User());
				grid.getSelectionModel().select(userStore.size() - 1, true);
				userWindow.show();
			}
		});
		deleteButton = new CustomTextButton("удалить");
		deleteButton.setEnabled(false);
		deleteButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				MessageBox box = new MessageBox(Organization.DELETE_CONFIRM, "");
				box.setPredefinedButtons(PredefinedButton.YES, PredefinedButton.NO);
				box.setIcon(MessageBox.ICONS.question());
				box.setMessage(Organization.DELETE_CONFIRM_MESSAGE);
				box.addDialogHideHandler(new DialogHideHandler() {
					@Override
					public void onDialogHide(DialogHideEvent event) {
						if (event.getHideButton().equals(PredefinedButton.YES))
							setNullType();
					}
				});
				box.show();
			}
		});
		buttons.add(addButton);
		buttons.add(deleteButton);
		outer.add(buttons, new VerticalLayoutData(1, -1, new Margins(10, 0, 0, 0)));

		gridPanel.setScrollMode(ScrollMode.AUTOY);
		gridPanel.add(grid, new CssFloatData(1));
		outer.add(gridPanel, new VerticalLayoutData(1, -1));
		return outer;
	}

	public void refreshUserList() {
		userService.getAll(new AsyncCallback<List<User>>() {
			public void onFailure(Throwable caught) {
				Info.display(Organization.ERROR_TYPE, Organization.ERROR_MESSAGE);
			}

			public void onSuccess(List<User> companyList) {
				userStore.clear();
				userStore.addAll(companyList);
				deleteButton.setEnabled(false);
				// user = userStore.get(0);
				grid.getView().refresh(true);
			}
		});
	}

	void setNullType() {
		companyService.setUserNull(user, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				Info.display(Organization.ERROR_TYPE, Organization.ERROR_MESSAGE);
			}

			public void onSuccess(Void result) {
				deleteUser();
			}
		});
	}

	void deleteUser() {
		userService.delete(user, new AsyncCallback<Void>() {
			public void onFailure(Throwable caught) {
				Info.display(Organization.ERROR_TYPE, Organization.ERROR_MESSAGE);
				caught.printStackTrace();
			}

			public void onSuccess(Void result) {
				refreshUserList();
				// grid.getSelectionModel().select(1, true);
			}
		});
	}

	void updateUser(User user) {
		userService.update(user, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				Info.display(Organization.ERROR_TYPE, Organization.ERROR_MESSAGE);
			}

			@Override
			public void onSuccess(Void result) {
				refreshUserList();
			}
		});
	}

	void createWindow() {
		CssFloatLayoutContainer outerPanel = new CssFloatLayoutContainer();
		CssFloatLayoutContainer innerPanel = new CssFloatLayoutContainer();
		userWindow = new Window();
		userWindow.setPixelSize(300, 150);
		userWindow.setResizable(false);
		userWindow.setModal(true);
		userWindow.setBlinkModal(true);
		userWindow.setHeadingText("добавить сотрудника");
		userWindow.setExpanded(true);
		userWindow.addHideHandler(new HideHandler() {
			@Override
			public void onHide(HideEvent event) {
				deleteUser();
			}
		});

		KeyUpHandler keyHandler = new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent arg0) {
				if (arg0.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					create();
				}
			}
		};

		name = new TextField();
		name.setAllowBlank(false);
		name.setValidateOnBlur(false);
		name.addKeyUpHandler(keyHandler);
		FieldLabel nameFieldLabel = new CustomFieldLabel(name, "имя");
		nameFieldLabel.setLabelWidth(50);
		innerPanel.add(nameFieldLabel, new CssFloatData(1));

		surname = new TextField();
		surname.setAllowBlank(false);
		surname.setValidateOnBlur(false);
		surname.addKeyUpHandler(keyHandler);

		FieldLabel surnameFieldLabel = new CustomFieldLabel(surname, "фамилия");
		surnameFieldLabel.setLabelWidth(50);
		innerPanel.add(surnameFieldLabel, new CssFloatData(1));

		patronymic = new TextField();
		patronymic.setAllowBlank(false);
		patronymic.setValidateOnBlur(false);
		patronymic.addKeyUpHandler(keyHandler);
		FieldLabel patronymicFieldLabel = new CustomFieldLabel(patronymic, "отчество");
		patronymicFieldLabel.setLabelWidth(50);
		innerPanel.add(patronymicFieldLabel, new CssFloatData(1));

		CustomTextButton addTypeButton = new CustomTextButton("добавить");
		addTypeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				create();
			}
		});
		innerPanel.setStyleFloat(Style.Float.RIGHT);
		innerPanel.add(addTypeButton);
		outerPanel.add(innerPanel, new CssFloatData(0.9, new Margins(10)));
		userWindow.setFocusWidget(name);
		userWindow.add(outerPanel);

	}

	public void create() {
		user = driver.flush();
		if (user.getName().trim() == "") {
			name.clear();
			new MessageBox(Organization.VERIFIER_MESSAGE).show();
			return;
		}
		if (user.getSurname().trim() == "") {
			surname.clear();
			new MessageBox(Organization.VERIFIER_MESSAGE).show();
			return;
		}
		if (user.getPatronymic().trim() == "") {
			patronymic.clear();
			new MessageBox(Organization.VERIFIER_MESSAGE).show();
			return;
		}
		if (driver.hasErrors()) {
			new MessageBox(Organization.VERIFIER_MESSAGE).show();
			return;
		}
		updateUser(user);
		name.clear();
		surname.clear();
		patronymic.clear();
		userWindow.hide();
	}
}
