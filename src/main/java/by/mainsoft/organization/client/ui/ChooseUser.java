package by.mainsoft.organization.client.ui;

import java.util.ArrayList;
import java.util.List;

import by.mainsoft.organization.client.Organization;
import by.mainsoft.organization.client.service.UserService;
import by.mainsoft.organization.client.service.UserServiceAsync;
import by.mainsoft.organization.shared.domain.User;
import by.mainsoft.organization.shared.domain.UserProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

public class ChooseUser implements IsWidget, Editor<User> {

	UserServiceAsync userService = GWT.create(UserService.class);
	private ListStore<User> userStore;
	private UserProperties props;
	private User user;
	private Grid<User> grid;
	private CustomTextButton selectButton;

	// private SearchPanel searchPanel;

	private VerticalLayoutContainer container;

	@Override
	public Widget asWidget() {
		if (container == null) {
			container = new VerticalLayoutContainer();
			props = GWT.create(UserProperties.class);
			userStore = new ListStore<User>(props.key());
			// searchPanel = new SearchPanel();
			refreshUserList("");

			container.add(createEditor());

		}
		return container;
	}

	public Widget createEditor() {
		CssFloatLayoutContainer inner = new CssFloatLayoutContainer();
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
		grid.setHideHeaders(false);
		grid.setHeight(100);
		grid.setBorders(true);
		grid.getView().setStripeRows(true);
		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<User>() {

			@Override
			public void onSelectionChanged(SelectionChangedEvent<User> event) {
				if (event.getSelection().size() > 0) {
					user = event.getSelection().get(0);
				}
			}
		});

		CssFloatLayoutContainer searchPanel = new CssFloatLayoutContainer();
		final TextBox searchBox = new TextBox();
		// searchBox.setStyleName("searchBox");
		StyleInjector.injectAtEnd(".my1 { border:1px solid; border-radius:15px;"
				+ "height:20px; padding-left:25px; background-image:url('images/search.png'); background-repeat:no-repeat; background-position: 2px;");
		searchBox.setStyleName("my1");
		searchBox.getElement().setPropertyString("placeholder", "поиск по вхождению");
		CustomTextButton searchButton = new CustomTextButton("найти");
		searchButton.addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				refreshUserList(searchBox.getText());
			}
		});

		searchPanel.add(searchBox, new CssFloatData(0.65, new Margins(0, 30, 0, 10)));
		searchPanel.add(searchButton);

		inner.add(searchPanel, new CssFloatData(0.9, new Margins(10, 0, 10, 0)));
		CssFloatLayoutContainer gridPanel = new CssFloatLayoutContainer();
		gridPanel.add(grid, new CssFloatData(1));
		gridPanel.setScrollMode(ScrollMode.AUTOY);
		inner.add(gridPanel, new CssFloatData(1, new Margins(0, 0, 5, 0)));
		selectButton = new CustomTextButton("выбрать");
		CssFloatLayoutContainer buttonPanel = new CssFloatLayoutContainer();
		buttonPanel.add(selectButton);
		inner.setStyleFloat(Style.Float.RIGHT);
		inner.add(buttonPanel, new CssFloatData(0.3));
		CssFloatLayoutContainer outer = new CssFloatLayoutContainer();
		outer.add(inner, new CssFloatData(1));
		return outer;
	}

	public void refreshUserList(String searchParameter) {
		userService.searchByString(searchParameter, new AsyncCallback<List<User>>() {
			public void onFailure(Throwable caught) {
				Info.display(Organization.ERROR_TYPE, Organization.ERROR_MESSAGE);
			}

			public void onSuccess(List<User> companyList) {
				userStore.clear();
				userStore.addAll(companyList);
				user = userStore.get(0);
				grid.getView().refresh(true);
			}
		});
	}

	public User getUser() {
		return user;
	}

	public HasSelectHandlers getSaveButton() {
		return selectButton;
	}

}
