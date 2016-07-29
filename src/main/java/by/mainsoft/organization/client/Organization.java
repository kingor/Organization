package by.mainsoft.organization.client;

import by.mainsoft.organization.client.ui.ButtonAlignExample;
import by.mainsoft.organization.client.ui.DirectoryTabView;
import by.mainsoft.organization.client.ui.OrganizationTabView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.PlainTabPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Organization implements IsWidget, EntryPoint {
	/**
	 * This is the entry point method.
	 */
	private VerticalPanel vp;

	final OrganizationTabView organizationTabView = new OrganizationTabView();
	final DirectoryTabView directoryTabView = new DirectoryTabView();
	final ButtonAlignExample buttonAlignExample = new ButtonAlignExample();

	public void onModuleLoad() {

		RootPanel.get("organizationPanel").add(asWidget());
	}

	@Override
	public Widget asWidget() {
		if (vp == null) {
			vp = new VerticalPanel();
			vp.setSpacing(10);
			PlainTabPanel tabPanel = new PlainTabPanel();
			VerticalPanel organizationPanel = new VerticalPanel();
			VerticalPanel directoryPanel = new VerticalPanel();

			organizationPanel.add(organizationTabView);
			organizationTabView.refreshCompanyList();
			directoryPanel.add(directoryTabView);

			tabPanel.setWidth(800);
			tabPanel.setHeight(500);
			tabPanel.add(organizationPanel, "Организации");
			tabPanel.add(directoryPanel, "Справочники");
			tabPanel.add(buttonAlignExample, "Example");

			tabPanel.addSelectionHandler(new SelectionHandler<Widget>() {

				@Override
				public void onSelection(SelectionEvent<Widget> event) {
					organizationTabView.refreshCompanyList();
				}
			});
			vp.add(tabPanel);
		}
		return vp;
	}

}
