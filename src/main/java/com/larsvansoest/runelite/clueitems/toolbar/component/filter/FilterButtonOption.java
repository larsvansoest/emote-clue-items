package com.larsvansoest.runelite.clueitems.toolbar.component.filter;

import java.awt.Color;
import javax.swing.Icon;

class FilterButtonOption<T>
{
	private final T value;
	private final Icon icon;
	private final Color borderColor;


	public FilterButtonOption(T value, Icon icon, Color borderColor) {
		this.value = value;
		this.icon = icon;
		this.borderColor = borderColor;
	}

	public T getValue()
	{
		return this.value;
	}

	public Icon getIcon()
	{
		return this.icon;
	}

	public Color getBorderColor()
	{
		return this.borderColor;
	}

}
