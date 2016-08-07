package by.mainsoft.organization.client.ui;

import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class SearchPanel implements IsWidget {

	private CssFloatLayoutContainer searchPanel;
	final TextBox searchBox = new TextBox();;
	private CustomTextButton searchButton;

	@Override
	public Widget asWidget() {
		if (searchPanel == null) {
			searchPanel = new CssFloatLayoutContainer();

			searchButton = new CustomTextButton("найти");

			StyleInjector.injectAtEnd(".my1 { border:1px solid; border-radius:15px; height:20px; padding-left:25px; background-image:url('images/search.png'); "
					+ "background-repeat:no-repeat; background-position: 2px;");
			searchBox.setStyleName("my1");
			searchBox.getElement().setPropertyString("placeholder", "поиск по вхождению");

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
