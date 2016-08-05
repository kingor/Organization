package by.mainsoft.organization.client.ui;

import com.google.gwt.dom.client.StyleInjector;
import com.sencha.gxt.widget.core.client.button.TextButton;

public class CustomTextButton extends TextButton {

	public CustomTextButton(String text) {
		super(text);
		StyleInjector.injectAtEnd(".myCustomStyle { border:1px solid; border-radius:15px;  min-width: 30px;");
		this.setMinWidth(30);
		this.setStyleName("myCustomStyle");
	}

}
