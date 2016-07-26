package by.mainsoft.organization.client;

import by.mainsoft.organization.client.ui.ListViewBindingExample;
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
		ListViewBindingExample formExample = new ListViewBindingExample();

		VerticalPanel organizationPanel = new VerticalPanel();
		VerticalPanel catalogPanel = new VerticalPanel();

		catalogPanel.add(formExample);
		organizationPanel.add(organizationTabView);
		tabPanel.setWidth(800);
		tabPanel.setHeight(500);
		tabPanel.add(organizationPanel, "Организации");
		tabPanel.add(catalogPanel, "Справочники");
		// tabPanel.selectTab(0);
		RootPanel.get("organizationPanel").add(formExample);/* .add(tabPanel); */
	}
}
