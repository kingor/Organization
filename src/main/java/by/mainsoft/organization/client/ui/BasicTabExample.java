package by.mainsoft.organization.client.ui;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.PlainTabPanel;

public class BasicTabExample implements IsWidget {

	private VerticalPanel vp;

	public Widget asWidget() {
		if (vp == null) {
			vp = new VerticalPanel();
			vp.setSpacing(10);

			SelectionHandler<Widget> handler = new SelectionHandler<Widget>() {
				@Override
				public void onSelection(SelectionEvent<Widget> event) {
					// TabPanel panel = (TabPanel) event.getSource();
					// Widget w = event.getSelectedItem();
					// TabItemConfig config = panel.getConfig(w);
					// Info.display("Message", "'" + config.getText() + "' Selected");
				}
			};

			final PlainTabPanel panel = new PlainTabPanel();
			panel.setPixelSize(800, 600);
			// panel.addSelectionHandler(handler);

			panel.add(new OrganizationTabView(), "Организации");
			panel.add(new OrganizationTabView(), "1dfg");
			panel.add(new ButtonAlignExample(), "1dfg");

			vp.add(panel);
		}

		return vp;
	}

}
