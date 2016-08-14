package by.mainsoft.organization.client;

import by.mainsoft.organization.client.ui.CompanyBinding;
import by.mainsoft.organization.client.ui.DirectoryBinding;

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

	public static final String ERROR_MESSAGE = "Данные не обновлены";
	public static final String ERROR_TYPE = "Ошибка";
	public static final String VERIFIER_MESSAGE = "Заполните необходимые поля";
	public static final String DELETE_CONFIRM = "Удалить?";
	public static final String DELETE_CONFIRM_MESSAGE = "Хотите удалить запись?";

	private VerticalPanel container;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		RootPanel.get("organizationPanel").add(asWidget());
	}

	@Override
	public Widget asWidget() {
		if (container == null) {
			container = new VerticalPanel();
			container.setSpacing(10);

			PlainTabPanel tabPanel = new PlainTabPanel();
			tabPanel.setWidth(800);

			final CompanyBinding companyBinding = new CompanyBinding();
			final DirectoryBinding directoryBinding = new DirectoryBinding();

			tabPanel.add(companyBinding, "Организации");
			tabPanel.add(directoryBinding, "Справочники");
			tabPanel.addSelectionHandler(new SelectionHandler<Widget>() {

				@Override
				public void onSelection(SelectionEvent<Widget> event) {
					companyBinding.getCompanyListDb();
				}
			});
			container.add(tabPanel);
		}
		return container;
	}

}
