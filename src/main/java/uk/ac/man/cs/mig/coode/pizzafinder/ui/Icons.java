package uk.ac.man.cs.mig.coode.pizzafinder.ui;

import javax.swing.*;
import java.net.URL;

public class Icons {
	public static ImageIcon getSpicyIcon() {
		URL url = Icons.class.getResource("/HotIcon.gif");
		return new ImageIcon(url);
	}

	public static ImageIcon getVegetarianIcon() {
		URL url = Icons.class.getResource("/VegetarianIcon.gif");
		return new ImageIcon(url);
	}

	public static ImageIcon getPizzaSliceIcon() {
		URL url = Icons.class.getResource("/PizzaSliceIcon.png");
		return new ImageIcon(url);
	}
}

