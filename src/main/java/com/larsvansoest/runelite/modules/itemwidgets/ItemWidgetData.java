package com.larsvansoest.runelite.modules.itemwidgets;

public class ItemWidgetData
{
	private ItemWidgetContainer container;
	private ItemWidgetContext context;

	public ItemWidgetData() {};

	public ItemWidgetContainer getContainer()
	{
		return container;
	}

	public void setContainer(ItemWidgetContainer container)
	{
		this.container = container;
	}

	public ItemWidgetContext getContext()
	{
		return context;
	}

	public void setContext(ItemWidgetContext context)
	{
		this.context = context;
	}
}
