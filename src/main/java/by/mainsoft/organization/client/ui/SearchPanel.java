package by.mainsoft.organization.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class SearchPanel implements IsWidget {

	private CssFloatLayoutContainer searchPanel;
	private TextBox searchBox;
	private final TextButton searchButton = new CustomTextButton("найти");

	@Override
	public Widget asWidget() {
		if (searchPanel == null) {
			searchPanel = new CssFloatLayoutContainer();

			searchBox = new TextBox();
			searchBox.getElement().setPropertyString("placeholder", "поиск по вхождению");
			// DOM.setElementAttribute(searchBox.getElement(), "id", "myTextBox");
			searchBox.getElement().setId("myTextBox");
			searchPanel.add(searchBox, new CssFloatData(0.65, new Margins(0, 30, 0, 10)));
			searchPanel.add(searchButton);
		}
		return searchPanel;
	}

	public HasSelectHandlers getSearchButton() {
		return searchButton;
	}

	public String getSearchText() {
		return searchBox.getText();
	}

	public void setHandler(SelectHandler sh) {
		searchButton.addSelectHandler(sh);
	}

}
