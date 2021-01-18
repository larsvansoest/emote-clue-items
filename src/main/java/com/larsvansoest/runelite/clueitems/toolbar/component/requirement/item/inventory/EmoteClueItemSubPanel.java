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

package com.larsvansoest.runelite.clueitems.toolbar.component.requirement.item.inventory;

import com.larsvansoest.runelite.clueitems.data.EmoteClueImage;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.RequirementStatus;
import com.larsvansoest.runelite.clueitems.toolbar.component.EmoteClueItemsPanelPalette;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.UpdatablePanel;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.foldable.FoldableHeaderText;
import com.larsvansoest.runelite.clueitems.toolbar.component.requirement.foldable.FoldablePanel;
import java.awt.GridBagConstraints;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.runelite.client.ui.ColorScheme;

public class EmoteClueItemSubPanel extends FoldablePanel
{
	private final static int ROW_SIZE = 6;

	private final GridBagConstraints foldContentConstraints;
	private Boolean expanded;
	private int completedAmount;
	private int requiredAmount;
	private final FoldableHeaderText amountLabel;

	public EmoteClueItemSubPanel(EmoteClueItemsPanelPalette emoteClueItemsPanelPalette) {
		super(emoteClueItemsPanelPalette, "Collection log");
		super.getFoldContent().setBackground(emoteClueItemsPanelPalette.getSubPanelBackgroundColor());
		super.setStatus(RequirementStatus.Unknown);

		JLabel nameLabel = super.getFoldableHeader().getNameLabel();
		nameLabel.setHorizontalAlignment(JLabel.LEFT);
		nameLabel.setPreferredSize(null);
		nameLabel.setMinimumSize(null);
		nameLabel.setMaximumSize(null);
		nameLabel.setForeground(ColorScheme.LIGHT_GRAY_COLOR);
		// TODO clean up code

		this.amountLabel = new FoldableHeaderText("");
		this.amountLabel.setHorizontalAlignment(JLabel.CENTER);

		super.addLeftIcon(new JLabel(new ImageIcon(EmoteClueImage.Toolbar.Requirement.INVENTORY)));

		this.foldContentConstraints = new GridBagConstraints();
		this.foldContentConstraints.weightx = 0;
		this.foldContentConstraints.fill = GridBagConstraints.BOTH;
		this.expanded = false;
	}

	@Override
	public void fold() {
		this.expanded = false;
		super.fold();
	}

	@Override
	public void unfold()
	{
		JPanel foldContent = super.getFoldContent();
		LinkedList<UpdatablePanel> itemSlots = super.getFoldContentElements();
		super.getFoldableHeader().unfold();
		this.foldContentConstraints.gridx = 0;
		this.foldContentConstraints.gridy = 0;

		int i = 0;
		while(i < itemSlots.size()) {
			foldContent.add(itemSlots.get(i), this.foldContentConstraints);
			i++;
			int x = i % ROW_SIZE;
			if (x == 0) {
				this.foldContentConstraints.gridy++;
			}
			this.foldContentConstraints.gridx = x;
		}

		this.expanded = true;
		foldContent.setVisible(true);
		super.revalidate();
		super.repaint();
	}

	@Override
	public void addChild(UpdatablePanel child)
	{
		super.addChild(child);
	}

	@Override
	public void onHeaderMousePressed()
	{
		if (this.expanded)
		{
			this.fold();
		}
		else
		{
			this.unfold();
		}
	}

	public void setRequiredAmount(int amount) {
		this.requiredAmount = amount;
		this.updateAmount();
	}

	public void setCompletedAmount(int amount) {
		this.completedAmount = amount;
		this.updateAmount();
	}

	private void updateAmount() {
		this.amountLabel.setText(String.format("%s/%s", this.completedAmount, this.requiredAmount));
	}

	public void addAmountLabel() {
		super.addRightIcon(this.amountLabel);
	}

	public void addStashUnitIcon(StashUnitLabel stashUnitLabel) {
		super.addRightIcon(stashUnitLabel);
	}

	public int getCompletedAmount()
	{
		return this.completedAmount;
	}

	public int getRequiredAmount()
	{
		return this.requiredAmount;
	}
}
