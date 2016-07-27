package by.mainsoft.organization.client;

import by.mainsoft.organization.client.ui.DirectoryTabView;
import by.mainsoft.organization.client.ui.OrganizationTabView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.PlainTabPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Organization implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		PlainTabPanel tabPanel = new PlainTabPanel();

		final OrganizationTabView organizationTabView = new OrganizationTabView();
		final DirectoryTabView directoryTabView = new DirectoryTabView();

		VerticalPanel organizationPanel = new VerticalPanel();
		VerticalPanel directoryPanel = new VerticalPanel();

		organizationPanel.add(organizationTabView);
		organizationTabView.refreshCompanyList();
		directoryPanel.add(directoryTabView);
		tabPanel.setWidth(800);
		tabPanel.setHeight(500);
		tabPanel.add(organizationPanel, "Организации");
		tabPanel.add(directoryPanel, "Справочники");

		tabPanel.addSelectionHandler(new SelectionHandler<Widget>() {

			@Override
			public void onSelection(SelectionEvent<Widget> event) {
				organizationTabView.refreshCompanyList();
			}
		});/*
			 * .addSelectionHandler(new SelectionHandler<Integer>() { public void onSelection(SelectionEvent<Integer> event) { int tabId = event.getSelectedItem(); Widget tabWidget = tabpanel.getWidget(tabId); } });
			 */
		// tabPanel.selectTab(0);
		RootPanel.get("organizationPanel").add(tabPanel);
	}
}
