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

package com.larsvansoest.runelite.clueitems.overlay;

import com.larsvansoest.runelite.clueitems.data.EmoteClueDifficulty;
import net.runelite.api.Client;
import net.runelite.api.widgets.WidgetItem;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Utility class with Runescape item container widget inspection functionality.
 *
 * @author Lars van Soest
 * @since 1.2.0
 */
public abstract class WidgetInspector
{
	/**
	 * Identifies the origin of given widgetItem, writes found data into given {@link ItemContainerWidgetData} object. The method iterates over the ancestors (parents of parents), and compares it to the ids of all entries of the {@link Widget} enum.
	 *
	 * @param widgetItem    the {@link WidgetItem} to analyse.
	 * @param itemContainerWidgetDataRef the {@link ItemContainerWidgetData} to write found data to.
	 * @param maxDepth      the maximum steps from initial widgetItem parameter to one of the parents specified.
	 * @since 1.2.0
	 */
	public static void InspectItemContainer(final WidgetItem widgetItem, final ItemContainerWidgetData itemContainerWidgetDataRef, final int maxDepth)
	{
		net.runelite.api.widgets.Widget widget = widgetItem.getWidget();

		itemContainerWidgetDataRef.setWidgetContainer(null);
		itemContainerWidgetDataRef.setWidgetContext(null);

		int i = 0;
		while (i < maxDepth && widget != null)
		{
			final int id = widget.getId();

			if (id == Widget.BANK.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InBank);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.Bank);
				return;
			}
			else if (id == Widget.BANK_EQUIPMENT.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InBank);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.Equipment);
				return;
			}
			else if (id == Widget.BANK_EQUIPMENT_INVENTORY.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InBank);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.Inventory);
				return;
			}
			else if (id == Widget.BANK_INVENTORY.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InBank);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.Inventory);
				return;
			}

			else if (id == Widget.EQUIPMENT.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.Default);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.Equipment);
				return;
			}
			else if (id == Widget.EQUIPMENT_EQUIPMENT.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InEquipment);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.Equipment);
				return;
			}
			else if (id == Widget.EQUIPMENT_INVENTORY.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InEquipment);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.Inventory);
				return;
			}

			else if (id == Widget.DEPOSIT_BOX.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InDepositBox);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.DepositBox);
				return;
			}

			else if (id == Widget.GUIDE_PRICES.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InGuidePrices);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.GuidePrices);
				return;
			}
			else if (id == Widget.GUIDE_PRICES_INVENTORY.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InGuidePrices);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.Inventory);
				return;
			}

			else if (id == Widget.INVENTORY.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.Default);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.Inventory);
				return;
			}

			else if (id == Widget.KEPT_ON_DEATH.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InKeptOnDeath);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.KeptOnDeath);
				return;
			}

			else if (id == Widget.SHOP.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InShop);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.Shop);
				return;
			}

			else if (id == Widget.SHOP_INVENTORY.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InShop);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.Inventory);
				return;
			}

			else if (id == Widget.GROUP_STORAGE.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InGroupStorage);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.GroupStorage);
			}

			else if (id == Widget.GROUP_STORAGE_INVENTORY.id)
			{
				itemContainerWidgetDataRef.setWidgetContext(WidgetContext.InGroupStorage);
				itemContainerWidgetDataRef.setWidgetContainer(WidgetContainer.Inventory);
			}

			widget = widget.getParent();
			i++;
		}
	}

	public static boolean TryReadWatsonBoard(final Client client, final Function<EmoteClueDifficulty, BiConsumer<String, Boolean>> stashUnitFillStatusCallback) {
		return TryReadWatsonBoard(Widget.WATSON_NOTICE_BOARD_BEGINNER, client, stashUnitFillStatusCallback.apply(EmoteClueDifficulty.Beginner))
			 | TryReadWatsonBoard(Widget.WATSON_NOTICE_BOARD_EASY, client, stashUnitFillStatusCallback.apply(EmoteClueDifficulty.Easy))
			 | TryReadWatsonBoard(Widget.WATSON_NOTICE_BOARD_MEDIUM, client, stashUnitFillStatusCallback.apply(EmoteClueDifficulty.Medium))
			 | TryReadWatsonBoard(Widget.WATSON_NOTICE_BOARD_HARD, client, stashUnitFillStatusCallback.apply(EmoteClueDifficulty.Hard))
			 | TryReadWatsonBoard(Widget.WATSON_NOTICE_BOARD_ELITE, client, stashUnitFillStatusCallback.apply(EmoteClueDifficulty.Elite))
			 | TryReadWatsonBoard(Widget.WATSON_NOTICE_BOARD_MASTER, client, stashUnitFillStatusCallback.apply(EmoteClueDifficulty.Master));
	}

	private static boolean TryReadWatsonBoard(Widget watsonNoticeBoard, final Client client, final BiConsumer<String, Boolean> stashUnitFillStatusCallback) {
		final net.runelite.api.widgets.Widget stashListWidget = client.getWidget(watsonNoticeBoard.groupId, watsonNoticeBoard.childId);
		if (Objects.isNull(stashListWidget)) return false;
		final net.runelite.api.widgets.Widget[] stashListWidgetChildren = stashListWidget.getChildren();
		if (Objects.isNull(stashListWidgetChildren)) return false;

		String location = null;
		int checkMarkCount = 0;
		for(net.runelite.api.widgets.Widget widget : stashListWidgetChildren) {
			final boolean widgetContainsLocationName = widget.getType() == 4;
			if (widgetContainsLocationName) {
				if (Objects.nonNull(location)) {
					final boolean stashUnitFilled = checkMarkCount == 2;
					stashUnitFillStatusCallback.accept(location, stashUnitFilled);
					checkMarkCount = 0;
				}
				location = widget.getText();
			}
			final boolean widgetContainsCheckMark = Objects.nonNull(location) && widget.getType() == 5;
			if(widgetContainsCheckMark) {
				checkMarkCount++;
			}
		}

		if (Objects.nonNull(location)) {
			final boolean stashUnitFilled = checkMarkCount == 2;
			stashUnitFillStatusCallback.accept(location, stashUnitFilled);
		}

		return true;
	}
}