/*
 * BSD 2-Clause License
 *
 * Copyright (c) 2020, Lars van Soest
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.larsvansoest.runelite.clueitems.toolbar.component.requirement;

import com.larsvansoest.runelite.clueitems.data.RequirementStatus;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.header.RequirementPanelHeader;
import com.larsvansoest.runelite.clueitems.toolbar.palette.EmoteClueItemsPanelPalette;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class RequirementPanel extends UpdatablePanel
{
	private final RequirementContainer parent;
	private final RequirementPanelHeader requirementPanelHeader;
	private final JPanel foldContent;
	private final LinkedList<JPanel> foldContentElements;
	private final GridBagConstraints foldConstraints;
	private final Map<String, Object> filterables;
	private Boolean expanded;

	public RequirementPanel(RequirementContainer parent, EmoteClueItemsPanelPalette emoteClueItemsPanelPalette, String name)
	{
		super.setLayout(new GridBagLayout());
		super.setBackground(emoteClueItemsPanelPalette.getDefaultColor());
		super.setName(name);

		this.parent = parent;
		this.expanded = false;
		this.requirementPanelHeader = new RequirementPanelHeader(this, emoteClueItemsPanelPalette, new Dimension(155, 20), name);

		this.foldContentElements = new LinkedList<>();
		this.foldContent = new JPanel(new GridBagLayout());
		this.foldContent.setBackground(emoteClueItemsPanelPalette.getFoldContentColor());
		this.foldConstraints = new GridBagConstraints();
		this.filterables = new HashMap<>();
		this.setFilterable("name", name);

		this.setStatus(RequirementStatus.InComplete);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		super.add(this.requirementPanelHeader, c);

		c.gridy++;
		this.foldContent.setVisible(this.expanded);
		super.add(this.foldContent, c);
	}

	public final void setStatus(RequirementStatus status)
	{
		this.requirementPanelHeader.getNameLabel().setForeground(status.colour);
		this.setFilterable("status", status);
	}

	public final void onHeaderMousePressed()
	{
		this.parent.toggleFold(this);
	}

	public final void fold()
	{
		this.foldContentElements.forEach(this.foldContent::remove);
		this.requirementPanelHeader.fold();
		this.foldContent.setVisible(false);
		this.expanded = false;
	}

	public final void unfold()
	{
		this.foldConstraints.gridy = 0;
		this.foldContentElements.forEach(element -> {
			this.foldContent.add(element, this.foldConstraints);
			this.foldConstraints.gridy++;
		});
		this.requirementPanelHeader.unfold();
		this.foldContent.setVisible(true);
		this.expanded = true;
	}

	public final void setFilterable(String key, Object value)
	{
		this.filterables.put(key, value);
	}

	public final Object getFilterable(String key)
	{
		return this.filterables.get(key);
	}

	public final Boolean satisfiesFilterable(String key, Object value)
	{
		Object filterValue = this.filterables.get(key);
		if (filterValue instanceof Collection<?>) {
			return ((Collection<?>) filterValue).stream().anyMatch(filterValueElement -> this.filterValueMatches(filterValueElement, value));
		}
		return this.filterValueMatches(filterValue, value);
	}

	private Boolean filterValueMatches(Object filterValue, Object value) {
		return filterValue == null
			|| value == null
			|| (value instanceof String) && (filterValue instanceof String) && ((String) filterValue).toLowerCase().contains(((String) value).toLowerCase())
			|| value.equals(filterValue);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public int compareTo(RequirementPanel requirementPanel, String sortKey) {
		Object value1 = this.filterables.get(sortKey);
		Object value2 = requirementPanel.getFilterable(sortKey);
		if(value1 == value2 || !value1.getClass().equals(value2.getClass()) || !(value1 instanceof Comparable)) return 0;
		return ((Comparable) value1).compareTo(value2);
	}

	public final void addChild(JPanel content)
	{
		this.foldContentElements.add(content);
	}

	public final void addIcon(JLabel icon)
	{
		this.requirementPanelHeader.addIcon(icon);
	}

	public final Boolean isExpanded()
	{
		return this.expanded;
	}
}