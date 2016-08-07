package by.mainsoft.organization.client.ui;

import com.google.gwt.user.client.DOM;
import com.sencha.gxt.widget.core.client.button.TextButton;

public class CustomTextButton extends TextButton {

	public CustomTextButton(String text) {
		super(text);
		// StyleInjector.injectAtEnd(".myCustomStyle { border:1px solid; border-radius:15px;  min-width: 30px;");
		this.setMinWidth(30);
		// this.setStyleName("myCustomStyle");
		DOM.setElementAttribute(this.getElement(), "id", "myButton");
	}

}
