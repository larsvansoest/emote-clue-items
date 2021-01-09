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

package com.larsvansoest.runelite.clueitems.toolbar.component.input;

import javax.swing.Icon;

class FilterButtonOption<T>
{
	private final T primaryValue;
	private final T secondaryValue;
	private final Icon primaryIcon;
	private final Icon secondaryIcon;
	private final String toolTip;

	public FilterButtonOption(T primaryValue, Icon primaryIcon, String toolTip) {
		this(primaryValue, null, primaryIcon, null, toolTip);
	}

	public FilterButtonOption(T primaryValue, T secondaryValue, Icon primaryIcon, Icon secondaryIcon, String toolTip) {
		this.primaryValue = primaryValue;
		this.secondaryValue = secondaryValue;
		this.primaryIcon = primaryIcon;
		this.secondaryIcon = secondaryIcon;
		this.toolTip = toolTip;
	}

	public T getPrimaryValue()
	{
		return this.primaryValue;
	}

	public T getSecondaryValue()
	{
		return this.secondaryValue;
	}

	public Icon getPrimaryIcon()
	{
		return this.primaryIcon;
	}

	public Icon getSecondaryIcon()
	{
		return this.secondaryIcon;
	}

	public String getToolTip() { return this.toolTip; }
}
