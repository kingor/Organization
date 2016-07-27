package by.mainsoft.organization.client;

import by.mainsoft.organization.client.ui.DirectoryTabView;
import by.mainsoft.organization.client.ui.OrganizationTabView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
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

		OrganizationTabView organizationTabView = new OrganizationTabView();
		DirectoryTabView directoryTabView = new DirectoryTabView();

		VerticalPanel organizationPanel = new VerticalPanel();
		VerticalPanel directoryPanel = new VerticalPanel();

		organizationPanel.add(organizationTabView);
		directoryPanel.add(directoryTabView);
		tabPanel.setWidth(800);
		tabPanel.setHeight(500);
		tabPanel.add(organizationPanel, "Организации");
		tabPanel.add(directoryPanel, "Справочники");
		// tabPanel.selectTab(0);
		RootPanel.get("organizationPanel").add(tabPanel);
	}
}
