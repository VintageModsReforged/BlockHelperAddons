package reforged.mods.blockhelper.addons.integrations.nei;

import codechicken.nei.forge.IContainerTooltipHandler;
import mods.vintage.core.platform.lang.Translator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import reforged.mods.blockhelper.addons.utils.ToolIntegrationHelper;

import java.util.List;

public class NEITooltipHandler implements IContainerTooltipHandler {

    @Override
    public List handleTooltipFirst(GuiContainer guiContainer, int i, int j, List currentTip) {
        return currentTip;
    }

    @Override
    public List handleItemTooltip(GuiContainer guiContainer, ItemStack stack, List currentTip) {
        if (ToolIntegrationHelper.isHelmet(stack)) {
            currentTip.add(Translator.GOLD.format("info.tooltip.tools.installed"));
            if (ToolIntegrationHelper.hasReader(stack)) {
                currentTip.add(Translator.WHITE.literal(" - ") + Translator.AQUA.format(ToolIntegrationHelper.Tools.READER.description));
            }

            if (ToolIntegrationHelper.hasAnalyzer(stack)) {
                currentTip.add(Translator.WHITE.literal(" - ") + Translator.AQUA.format(ToolIntegrationHelper.Tools.CROPNALYZER.description));
            }

            if (ToolIntegrationHelper.hasTreeTap(stack)) {
                currentTip.add(Translator.WHITE.literal(" - ") + Translator.AQUA.format(ToolIntegrationHelper.Tools.TREETAP.description));
            }
        }

        return currentTip;
    }
}
