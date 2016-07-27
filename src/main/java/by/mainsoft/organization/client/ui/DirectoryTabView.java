package by.mainsoft.organization.client.ui;

import java.util.List;

import by.mainsoft.organization.client.service.TypeService;
import by.mainsoft.organization.client.service.TypeServiceAsync;
import by.mainsoft.organization.client.service.UserService;
import by.mainsoft.organization.client.service.UserServiceAsync;
import by.mainsoft.organization.shared.domain.Type;
import by.mainsoft.organization.shared.domain.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class DirectoryTabView extends Composite {

	private static DirectoryTabViewUiBinder uiBinder = GWT.create(DirectoryTabViewUiBinder.class);
	private UserServiceAsync userService = GWT.create(UserService.class);
	private TypeServiceAsync typeService = GWT.create(TypeService.class);

	interface DirectoryTabViewUiBinder extends UiBinder<Widget, DirectoryTabView> {
	}

	@UiField
	ListBox directoryList;

	@UiField
	FlexTable flexTable;

	@UiField
	Button addButton;

	@UiField
	Button deleteButton;

	private List<User> userAll;
	private List<Type> typeAll;
	private int selectedTableRow = -1;

	public DirectoryTabView() {
		initWidget(uiBinder.createAndBindUi(this));
		initList();
		chooseSelectedDirectory(0);
	}

	private void initList() {
		directoryList.clear();
		directoryList.addItem("Типы организаций");
		directoryList.addItem("Пользователи");
		directoryList.setSelectedIndex(0);
	}

	private void initTypeTable() {
		flexTable.clear();
		flexTable.removeAllRows();
	}

	private void initUserTable() {
		flexTable.clear();
		flexTable.removeAllRows();
		flexTable.setText(0, 0, "Фамилия");
		flexTable.setText(0, 1, "Имя");
		flexTable.setText(0, 2, "Отчество");
	}

	private void setTypeContext(List<Type> typeList) {
		initTypeTable();
		int row = 0;
		for (Type type : typeList) {
			flexTable.setText(row, 0, type.getName());
			row++;
		}
	}

	private void setUserContext(List<User> userList) {
		initUserTable();
		int row = 1;
		for (User user : userList) {
			flexTable.setText(row, 0, user.getSurname());
			flexTable.setText(row, 1, user.getName());
			flexTable.setText(row, 2, user.getPatronymic());
			row++;
		}
	}

	private void chooseSelectedDirectory(int selectedRow) {
		if (selectedRow == 0) {
			typeService.getAll(new AsyncCallback<List<Type>>() {
				public void onFailure(Throwable caught) {
					Window.alert("getAll for Type on Falure");
				}

				@Override
				public void onSuccess(List<Type> typeList) {
					setTypeContext(typeList);
					typeAll = typeList;
				}
			});
		} else if (selectedRow == 1) {
			userService.getAll(new AsyncCallback<List<User>>() {
				public void onFailure(Throwable caught) {
					Window.alert("getAll for User on Falure");
				}

				@Override
				public void onSuccess(List<User> userList) {
					setUserContext(userList);
					userAll = userList;
				}
			});
		}
	}

	@UiHandler("directoryList")
	void onOrganizationChange(ChangeEvent event) {
		chooseSelectedDirectory(directoryList.getSelectedIndex());
	}

	@UiHandler("flexTable")
	void onTableClicked(ClickEvent event) {
		// Select the row that was clicked (-1 to account for header row).
		Cell cell = flexTable.getCellForEvent(event);
		if (cell != null) {
			int row = cell.getRowIndex() - 1;
			selectRow(row);
		}
	}

	@UiHandler("deleteButton")
	void onDeleteRow(ClickEvent event) {

		if (directoryList.getSelectedIndex() == 1) {
			if (selectedTableRow > -1) {
				User user = userAll.get(selectedTableRow);
				Window.alert(user.getName());
				userService.delete(user, new AsyncCallback<Void>() {
					public void onFailure(Throwable caught) {
						Window.alert("delete Async callback не работает!");
						caught.printStackTrace();
					}

					public void onSuccess(Void result) {
						userAll.remove(selectedTableRow - 1);
						chooseSelectedDirectory(1);
					}
				});
			} else {
				Window.alert("don`t selected table" + selectedTableRow);
			}
		} else {
			Window.alert("don`t selected directoryList");
		}

	}

	private void selectRow(int row) {
		// When a row (other than the first one, which is used as a header) is
		// selected, display its associated MailItem.
		User item = userAll.get(row);
		if (item == null) {
			return;
		}

		selectedTableRow = row;
		Window.alert(item.getName());
		styleRow(selectedTableRow, false);
		styleRow(row, true);
		flexTable.getRowFormatter().setStyleName(row, "selectedRow");
	}

	private void styleRow(int row, boolean selected) {
		if (row != -1) {
			String style = "selectedRow";

			if (selected) {
				flexTable.getRowFormatter().setStyleName(row, style);
			} else {
				flexTable.getRowFormatter().removeStyleName(row, style);
			}
		}
	}

}
