package by.mainsoft.organization.client.ui;

import by.mainsoft.organization.shared.domain.Type;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

public class DirectoryBinding implements IsWidget, Editor<Type> {

	private CssFloatLayoutContainer panel;

	@Override
	public Widget asWidget() {
		if (panel == null) {
			panel = new CssFloatLayoutContainer();
			VerticalLayoutContainer choosePanel = new VerticalLayoutContainer();
			ListBox directoryList = new ListBox();
			directoryList.addItem("Типы организаций");
			directoryList.addItem("Пользователи");
			directoryList.setVisibleItemCount(3);
			// directoryList.setStyleName(style);
			choosePanel.add(directoryList, new VerticalLayoutData(1, 1));
			choosePanel.setBorders(true);
			panel.add(choosePanel, new CssFloatData(0.2));

			panel.add(new TypeWidget(), new CssFloatData(0.8));

		}
		return panel;
	}

}
