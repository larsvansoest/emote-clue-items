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

package com.larsvansoest.runelite.clueitems.ui.content.foldable;

import javax.swing.*;
import java.awt.*;

public class HeaderIconsPanel extends JPanel
{
	private final GridBagConstraints c;

	public HeaderIconsPanel(final boolean alignRight, final JLabel... iconLabels)
	{
		super(new GridBagLayout());
		super.setBackground(new Color(0, 0, 0, 0));
		this.c = new GridBagConstraints();
		this.c.gridx = 0;
		this.c.gridy = 0;
		this.c.fill = GridBagConstraints.BOTH;
		this.c.weightx = 0;

		if (alignRight)
		{
			this.c.anchor = GridBagConstraints.EAST;
			this.c.insets.left = 5;
		}
		else
		{
			this.c.anchor = GridBagConstraints.WEST;
			this.c.insets.right = 5;
		}

		for (final JLabel iconLabel : iconLabels)
		{
			this.addIcon(iconLabel);
		}
	}

	public void addIcon(final JComponent iconLabel)
	{
		super.add(iconLabel, this.c);
		this.c.gridx++;
	}
}
