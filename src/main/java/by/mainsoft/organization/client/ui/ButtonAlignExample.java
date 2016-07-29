package by.mainsoft.organization.client.ui;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.TextField;

public class ButtonAlignExample implements IsWidget {

	private VerticalPanel vp;

	public Widget asWidget() {
		if (vp == null) {
			vp = new VerticalPanel();
			TextButton b = new TextButton("Button " + 1);
			vp.add(b);
			createForm2();
		}

		return vp;
	}

	public void onModuleLoad() {
		RootPanel.get().add(asWidget());
	}

	private void createForm2() {
		VerticalPanel form2 = new VerticalPanel();
		// form2.setHeadingText("Simple Form with FieldSets");
		// form2.setWidth(350);

		VerticalLayoutContainer p = new VerticalLayoutContainer();
		form2.add(p);

		TextField firstName = new TextField();
		firstName.setAllowBlank(false);
		p.add(new FieldLabel(firstName, "First Name"), new VerticalLayoutData(.5, -1));

		TextField lastName = new TextField();
		lastName.setAllowBlank(false);
		p.add(new FieldLabel(lastName, "Last Name"), new VerticalLayoutData(.3, -1));

		TextField email = new TextField();
		email.setAllowBlank(false);
		p.add(new FieldLabel(email, "Email"), new VerticalLayoutData(1, -1));

		form2.add(new TextButton("Save"));
		form2.add(new TextButton("Cancel"));

		vp.add(form2);
	}
}
