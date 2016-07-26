package by.mainsoft.organization.client.ui;

import java.util.List;

import by.mainsoft.organization.client.service.CompanyService;
import by.mainsoft.organization.client.service.CompanyServiceAsync;
import by.mainsoft.organization.client.ui.ListViewBindingExample.CompanyExchange;
import by.mainsoft.organization.shared.domain.Company;
import by.mainsoft.organization.shared.domain.CompanyProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;

/**
 * Demonstrates using the ListStoreEditor, and some concept of building multiple editors. Note that as currently written, when a Stock object is saved, it will modify the StockExchange's instances, instead of cloning models before editing them.
 */

public class ListViewBindingExample implements /* EntryPoint, */IsWidget, Editor<CompanyExchange> {
	private CompanyServiceAsync companyService = GWT.create(CompanyService.class);

	public static class CompanyExchange {
		private List<Company> stocks = TestData.getComps();

		public List<Company> getStocks() {
			return stocks;
		}

		public void setStocks(List<Company> stocks) {
			this.stocks = stocks;
		}
	}

	interface ListDriver extends SimpleBeanEditorDriver<CompanyExchange, ListViewBindingExample> {
	}

	interface StockDriver extends SimpleBeanEditorDriver<Company, StockEditor> {
	}

	private ListDriver driver = GWT.create(ListDriver.class);

	private StockDriver itemDriver = GWT.create(StockDriver.class);

	private FramedPanel panel;

	ListView<Company, String> stockList;
	ListStoreEditor<Company> stocks;

	@Ignore
	StockEditor stockEditor;

	@Override
	public Widget asWidget() {
		if (panel == null) {
			panel = new FramedPanel();
			panel.setHeadingText("ListView Binding");
			panel.setPixelSize(300, 300);
			panel.setBodyBorder(false);

			panel.addStyleName("margin-10");

			VerticalLayoutContainer c = new VerticalLayoutContainer();

			final CompanyProperties props = GWT.create(CompanyProperties.class);

			stockList = new ListView<Company, String>(new ListStore<Company>(props.key()), props.name());
			stockList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

			stocks = new ListStoreEditor<Company>(stockList.getStore());

			c.add(stockList, new VerticalLayoutData(1, 1));
			stockList.getSelectionModel().addSelectionChangedHandler(new SelectionChangedHandler<Company>() {
				@Override
				public void onSelectionChanged(SelectionChangedEvent<Company> event) {
					if (event.getSelection().size() > 0) {
						edit(event.getSelection().get(0));
					} else {
						stockEditor.setSaveEnabled(false);
					}
				}
			});

			stockEditor = new StockEditor();

			stockEditor.getSaveButton().addSelectHandler(new SelectHandler() {

				@Override
				public void onSelect(SelectEvent event) {
					saveCurrentStock();

				}
			});
			c.add(stockEditor, new VerticalLayoutData(1, -1, new Margins(5)));
			panel.add(c);

			itemDriver.initialize(stockEditor);
			driver.initialize(this);
		}

		driver.edit(new CompanyExchange());
		return panel;
	}

	/*
	 * @Override public void onModuleLoad() { RootPanel.get().add(this); }
	 */

	protected void edit(Company stock) {
		itemDriver.edit(stock);
		stockEditor.setSaveEnabled(true);
	}

	protected void saveCurrentStock() {
		Company edited = itemDriver.flush();
		if (!itemDriver.hasErrors()) {
			stockEditor.setSaveEnabled(false);

			stockList.getStore().update(edited);
		}
	}
}
