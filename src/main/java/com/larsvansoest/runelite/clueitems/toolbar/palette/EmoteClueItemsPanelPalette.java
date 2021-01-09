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

package com.larsvansoest.runelite.clueitems.toolbar.palette;

import java.awt.Color;
import net.runelite.client.ui.ColorScheme;

public enum EmoteClueItemsPanelPalette
{
	DARK(ColorScheme.DARKER_GRAY_COLOR, ColorScheme.DARKER_GRAY_HOVER_COLOR, ColorScheme.DARKER_GRAY_HOVER_COLOR, ColorScheme.DARK_GRAY_HOVER_COLOR, ColorScheme.DARK_GRAY_HOVER_COLOR, ColorScheme.BRAND_ORANGE, ColorScheme.MEDIUM_GRAY_COLOR);

	private final Color defaultColor;
	private final Color hoverColor;
	private final Color selectColor;
	private final Color foldContentColor;
	private final Color foldContentElementColor;
	private final Color separatorColor;
	private final Color footerColor;

	EmoteClueItemsPanelPalette(Color defaultColor, Color hoverColor, Color selectColor, Color foldContentColor, Color foldContentElementColor, Color separatorColor, Color footerColor) {
		this.defaultColor = defaultColor;
		this.hoverColor = hoverColor;
		this.selectColor = selectColor;
		this.foldContentColor = foldContentColor;
		this.foldContentElementColor = foldContentElementColor;
		this.separatorColor = separatorColor;
		this.footerColor = footerColor;
	}

	public Color getDefaultColor()
	{
		return this.defaultColor;
	}

	public Color getHoverColor()
	{
		return this.hoverColor;
	}

	public Color getSelectColor()
	{
		return this.selectColor;
	}

	public Color getFoldContentColor()
	{
		return this.foldContentColor;
	}

	public Color getFoldContentElementColor() {
		return this.foldContentElementColor;
	}

	public Color getSeparatorColor() { return this.separatorColor; }

	public Color getFooterColor() { return this.footerColor; }
}
