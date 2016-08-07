package by.mainsoft.organization.client.ui;

import java.util.ArrayList;
import java.util.List;

import by.mainsoft.organization.client.Organization;
import by.mainsoft.organization.client.service.TypeService;
import by.mainsoft.organization.client.service.TypeServiceAsync;
import by.mainsoft.organization.shared.domain.Type;
import by.mainsoft.organization.shared.domain.TypeProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
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

public class ChooseType implements IsWidget/* , Editor<Type> */{

	TypeServiceAsync typeService = GWT.create(TypeService.class);
	private ListStore<Type> typeStore;
	private TypeProperties props;
	private Type type;
	private Grid<Type> grid;
	private CustomTextButton selectButton;

	private SearchPanel searchPanel;

	private VerticalLayoutContainer container;

	@Override
	public Widget asWidget() {
		if (container == null) {
			container = new VerticalLayoutContainer();
			props = GWT.create(TypeProperties.class);
			typeStore = new ListStore<Type>(props.key());
			searchPanel = new SearchPanel();
			refreshTypeList("");

			container.add(createWidget());

		}
		return container;
	}

	public Widget createWidget() {
		CssFloatLayoutContainer inner = new CssFloatLayoutContainer();
		ColumnConfig<Type, String> nameColumn = new ColumnConfig<Type, String>(props.name(), 100, "Name");
		List<ColumnConfig<Type, ?>> columns = new ArrayList<ColumnConfig<Type, ?>>();
		columns.add(nameColumn);
		grid = new Grid<Type>(typeStore, new ColumnModel<Type>(columns));
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.getView().setForceFit(true);
		grid.getView().setAutoExpandColumn(nameColumn);
		grid.setHideHeaders(true);
		grid.setHeight(100);
		grid.setBorders(true);
		grid.getView().setStripeRows(true);
		grid.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Type>() {

			@Override
			public void onSelectionChanged(SelectionChangedEvent<Type> event) {
				if (event.getSelection().size() > 0) {
					type = event.getSelection().get(0);
				}
			}
		});

		searchPanel.getSearchButton().addSelectHandler(new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {
				refreshTypeList(searchPanel.getSearchText());
			}
		});

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

	public void refreshTypeList(String searchParameter) {
		typeService.searchByString(searchParameter, new AsyncCallback<List<Type>>() {
			public void onFailure(Throwable caught) {
				Info.display(Organization.ERROR_TYPE, Organization.ERROR_MESSAGE);
			}

			public void onSuccess(List<Type> companyList) {
				typeStore.clear();
				typeStore.addAll(companyList);
				type = typeStore.get(0);
				grid.getView().refresh(true);
			}
		});
	}

	public Type getType() {
		return type;
	}

	public HasSelectHandlers getSelectButton() {
		return selectButton;
	}

}
