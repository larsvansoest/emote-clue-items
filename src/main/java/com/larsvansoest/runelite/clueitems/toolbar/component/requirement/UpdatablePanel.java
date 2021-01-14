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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import javax.swing.JPanel;

public abstract class UpdatablePanel extends JPanel
{
	private final JPanel foldContent;
	private final LinkedList<UpdatablePanel> foldContentElements;
	private final GridBagConstraints foldConstraints;

	public UpdatablePanel() {
		this.foldContent = new JPanel(new GridBagLayout());
		this.foldContentElements = new LinkedList<>();
		this.foldConstraints = new GridBagConstraints();
	}

	public void addKid(UpdatablePanel kid) {
		this.foldContentElements.add(kid);
	}

	public void foldKids() {
		this.foldContentElements.forEach(this.foldContent::remove);
		this.foldContent.setVisible(false);
		this.foldContentElements.forEach(UpdatablePanel::foldKids);
	}

	public void unfoldKids() {
		this.foldConstraints.gridy = 0;
		this.foldContentElements.forEach(element -> {
			this.foldContent.add(element, this.foldConstraints);
			this.foldConstraints.gridy++;
		});
		this.foldContentElements.forEach(UpdatablePanel::unfoldKids);
	}

	public abstract void setStatus(RequirementStatus requirementStatus);
	{
	}
}
