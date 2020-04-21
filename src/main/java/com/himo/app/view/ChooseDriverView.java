package com.himo.app.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.himo.app.entity.user.User;
import com.himo.app.service.user.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "chooseDriver", layout = MainView.class)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@UIScope
public class ChooseDriverView extends VerticalLayout
{

	private static final long serialVersionUID = 4153761837545371752L;

	@Autowired
	private UserService userService;

	@PostConstruct
	public void init()
	{
		User user = userService.getLoggenInUser();
		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.setItems("Fahrer", "Mitfahrer");

		Button button = new Button("los geht's!");
		button.addClickListener(evt ->
		{
			boolean bool = calcIsFahrer(comboBox.getValue());
			user.setFahrer(bool);
			user.setMitfahrer(!bool);
			userService.update(user);
			navigate();
		});

		H4 label = new H4();
		addClassName("centered-content");

		if (user != null)
		{
			label = new H4("Hi " + user.getFirstName() + "! Bist du Fahrer oder Mitfahrer?");
		} else
		{
			comboBox.setVisible(false);
			button.setVisible(false);
			label = new H4("Hi! Bitte log dich erst ein, bevor es weitergeht");

		}

//		Checkbox checkBox = new Checkbox("als Favorit markieren");
//		checkBox.setEnabled(false);
//		
//		comboBox.addValueChangeListener(evt -> checkBox.setEnabled(true));

		add(label, comboBox, button);
	}

	private boolean calcIsFahrer(String comboBoxValue)
	{
		if (comboBoxValue.equals("Fahrer"))
		{
			return true;
		}
		return false;
	}

	private void navigate()
	{
		UI.getCurrent().navigate(WayView.class);
	}
}
