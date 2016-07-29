package by.mainsoft.organization.client.ui;

import java.util.ArrayList;
import java.util.List;

import by.mainsoft.organization.shared.domain.Company;
import by.mainsoft.organization.shared.domain.Type;
import by.mainsoft.organization.shared.domain.TypeProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.client.editor.ListStoreEditor;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.button.ButtonBar;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel.LabelAlign;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;

public class CompanyEditor implements IsWidget, Editor<Company> {

	private static final TypeProperties props = GWT.create(TypeProperties.class);

	ListStore<Type> kidStore = new ListStore<Type>(props.key());
	ListStoreEditor<Type> kids = new ListStoreEditor<Type>(kidStore);

	private FlowPanel container;

	@Override
	public Widget asWidget() {
		if (container == null) {
			container = new FlowPanel();

			// should be layout based
			int w = 275;

			List<ColumnConfig<Type, ?>> columns = new ArrayList<ColumnConfig<Type, ?>>();
			final CheckBoxSelectionModel<Type> selection = new CheckBoxSelectionModel<Type>();
			selection.setSelectionMode(SelectionMode.MULTI);
			columns.add(selection.getColumn());
			ColumnConfig<Type, String> name = new ColumnConfig<Type, String>(props.name(), 200, "Name");
			columns.add(name);

			final Grid<Type> grid = new Grid<Type>(kidStore, new ColumnModel<Type>(columns));
			grid.setBorders(true);
			grid.setSelectionModel(selection);

			grid.getView().setForceFit(true);
			// GridInlineEditing<Type> inlineEditor = new GridInlineEditing<Type>(grid);
			// inlineEditor.addEditor(name, new TextField());
			// inlineEditor.addEditor(age, new IntegerField());

			grid.setWidth(382);
			grid.setHeight(200);

			FieldLabel kidsContainer = new FieldLabel();
			kidsContainer.setText("Kids");
			kidsContainer.setLabelAlign(LabelAlign.TOP);
			kidsContainer.setWidget(grid);
			container.add(kidsContainer);

			TextButton deleteBtn = new TextButton("Delete selected rows", new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					for (Type entityToDelete : selection.getSelectedItems()) {
						kidStore.remove(entityToDelete);
					}
				}
			});
			TextButton createBtn = new TextButton("Create new row", new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					Type newRow = new Type();
					newRow.setName("");
					kidStore.add(newRow);
				}
			});
			ButtonBar buttons = new ButtonBar();
			buttons.add(deleteBtn);
			buttons.add(createBtn);
			container.add(buttons);

		}
		return container;
	}

}
