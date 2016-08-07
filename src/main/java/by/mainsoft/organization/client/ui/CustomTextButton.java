package by.mainsoft.organization.client.ui;

import com.sencha.gxt.widget.core.client.button.TextButton;

public class CustomTextButton extends TextButton {

	public CustomTextButton(String text) {
		super(text);
		this.setMinWidth(30);
		// DOM.setElementAttribute(this.getElement(), "id", "myButton");
		this.getElement().setId("myButton");
	}

}
