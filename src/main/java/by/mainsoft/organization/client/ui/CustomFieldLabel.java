package by.mainsoft.organization.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

public class CustomFieldLabel extends FieldLabel {

	public CustomFieldLabel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomFieldLabel(IsWidget widget, FieldLabelAppearance appearance) {
		super(widget, appearance);
		// TODO Auto-generated constructor stub
	}

	public CustomFieldLabel(IsWidget widget, String label, FieldLabelAppearance appearance) {
		super(widget, label, appearance);
		// TODO Auto-generated constructor stub
	}

	public CustomFieldLabel(IsWidget widget, String label) {
		super(widget, label);
		this.setLabelSeparator("");
		// this.setLabelWidth(95);
		this.setLabelPad(10);
		DOM.setElementAttribute(this.getElement(), "id", "myLabel");
	}

	public CustomFieldLabel(IsWidget widget) {
		super(widget);
		// TODO Auto-generated constructor stub
	}

	public CustomFieldLabel(Widget widget, FieldLabelAppearance appearance) {
		super(widget, appearance);
		// TODO Auto-generated constructor stub
	}

}
