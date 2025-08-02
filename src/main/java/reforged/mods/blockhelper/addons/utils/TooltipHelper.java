package reforged.mods.blockhelper.addons.utils;

import mcp.mobius.waila.api.ITaggedList;
import mcp.mobius.waila.api.SpecialChars;
import reforged.mods.blockhelper.addons.utils.interfaces.ITooltipHelper;

public class TooltipHelper implements ITooltipHelper {

    @Override
    public void text(ITaggedList<String, String> tooltip, String text, boolean centered) {
        applyTooltipData(tooltip, TooltipData.textOnly(text, centered));
    }

    @Override
    public void bar(ITaggedList<String, String> tooltip, int current, int max, String text, int color) {
        applyTooltipData(tooltip, TooltipData.bar(current, max, color, text));
    }

    @Override
    public void fluid(ITaggedList<String, String> tooltip, int current, int max, String text, String fluidId) {
        applyTooltipData(tooltip, TooltipData.fluid(current, max, text, fluidId));
    }

    @Override
    public void applyTooltipData(ITaggedList<String, String> tooltip, TooltipData data) {
        tooltip.add(SpecialChars.getRenderString("jade.progress", (Object) data.toArray()));
    }
}
