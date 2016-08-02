package by.mainsoft.organization.client.ui;

import by.mainsoft.organization.shared.domain.Type;

import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer;
import com.sencha.gxt.widget.core.client.container.CssFloatLayoutContainer.CssFloatData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;

public class DirectoryBinding implements IsWidget, Editor<Type> {

	public static final int HEIGHT = 300;
	private CssFloatLayoutContainer panel;
	private CssFloatLayoutContainer typePanel;
	private CssFloatLayoutContainer userPanel;
	private ListBox directoryList;
	private TypeWidget typeWidget;
	private UserWidget userWidget;

	@Override
	public Widget asWidget() {
		if (panel == null) {
			panel = new CssFloatLayoutContainer();
			panel.setHeight(HEIGHT);

			typePanel = new CssFloatLayoutContainer();
			typeWidget = new TypeWidget();
			typePanel.add(typeWidget, new CssFloatData(1));

			userPanel = new CssFloatLayoutContainer();
			userWidget = new UserWidget();
			userPanel.add(userWidget, new CssFloatData(1));

			directoryList = new ListBox();
			directoryList.addItem("Типы организаций");
			directoryList.addItem("Пользователи");
			directoryList.setVisibleItemCount(3);

			VerticalLayoutContainer choosePanel = new VerticalLayoutContainer();
			choosePanel.add(directoryList, new VerticalLayoutData(1, 1));
			choosePanel.setBorders(true);
			choosePanel.setHeight(HEIGHT);

			panel.add(choosePanel, new CssFloatData(0.2));
			panel.add(typePanel, new CssFloatData(0.8));
			panel.add(userPanel, new CssFloatData(0.8));

			directoryList.addChangeHandler(new ChangeHandler() {

				@Override
				public void onChange(ChangeEvent event) {
					chooseDirectory(directoryList.getSelectedIndex());
				}
			});

		}
		return panel;
	}

	void chooseDirectory(int index) {
		if (directoryList.getSelectedIndex() != 0) {
			typePanel.hide();
			userPanel.show();
		} else {
			userPanel.hide();
			typePanel.show();
		}
	}
}
