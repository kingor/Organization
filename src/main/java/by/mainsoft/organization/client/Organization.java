package by.mainsoft.organization.client;

import by.mainsoft.organization.client.ui.OrganizationTabView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Organization implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		TabPanel tabPanel = new TabPanel();

		OrganizationTabView organizationTabView = new OrganizationTabView();

		VerticalPanel organizationPanel = new VerticalPanel();
		VerticalPanel catalogPanel = new VerticalPanel();
		organizationPanel.add(organizationTabView);
		tabPanel.add(organizationPanel, "Организации");
		tabPanel.add(catalogPanel, "Справочники");
		tabPanel.selectTab(0);
		RootPanel.get("organizationPanel").add(tabPanel);
	}
}
